package pe.gad.appbank.userservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gad.appbank.userservice.application.ports.input.CreateUserUseCase;
import pe.gad.appbank.userservice.application.ports.input.dto.CreateUserCommand;
import pe.gad.appbank.userservice.application.ports.input.dto.UserDto;
import pe.gad.appbank.userservice.application.ports.output.OutboxPersistencePort;
import pe.gad.appbank.userservice.application.ports.output.PasswordHasherPort;
import pe.gad.appbank.userservice.application.ports.output.RolePersistencePort;
import pe.gad.appbank.userservice.application.ports.output.UserPersistencePort;
import pe.gad.appbank.userservice.domain.enums.DocumentType;
import pe.gad.appbank.userservice.domain.exception.RoleNotFoundException;
import pe.gad.appbank.userservice.domain.exception.UserAlreadyExistsException;
import pe.gad.appbank.userservice.domain.model.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateUserService implements CreateUserUseCase {
    private final UserPersistencePort userPersistencePort;
    private final PasswordHasherPort passwordHasherPort;
    private final OutboxPersistencePort outboxPersistencePort;
    private final RolePersistencePort rolePersistencePort;
    public static final String DEFAULT_ROLE_NAME = "ROLE_USER";

    @Override
    @Transactional
    public UserDto createUser(CreateUserCommand command) {
        Email email = Email.of(command.email());

        if (userPersistencePort.existsUserByEmail(email)) {
            throw new UserAlreadyExistsException("User with email " + email.value() + " already exists");
        }
        if (userPersistencePort.existsUserByDocumentNumber(command.documentNumber())) {
            throw new UserAlreadyExistsException("User with document number " + command.documentNumber() + " already exists");
        }

        Role defaultRole = rolePersistencePort.findRoleByName(DEFAULT_ROLE_NAME)
                .orElseThrow(() -> new RoleNotFoundException("Default role not found: " + DEFAULT_ROLE_NAME));

        Password rawPassword = Password.raw(command.password());

        String passwordHash = passwordHasherPort.encode(rawPassword.value());

        Password encodedPassword = Password.encoded(passwordHash);

        PersonalInfo personalInfo = PersonalInfo.of(
                command.firstName(),
                command.lastName(),
                command.phone(),
                command.documentNumber()
        );

        DocumentType documentType = DocumentType.valueOf(command.documentType());

        User user = User.create(email, encodedPassword, personalInfo, defaultRole, documentType,
                command.documentIssuedDate(), command.documentExpiryDate());

        User userSaved = userPersistencePort.saveUser(user);

        List<Object> domainEvents = user.pullDomainEvents();

        outboxPersistencePort.saveAll(domainEvents);

        return mapToUserDto(userSaved);
    }

    private UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId().value().toString())
                .completeName(user.getPersonalInfo().getFullName())
                .email(user.getEmail().value())
                .phone(user.getPersonalInfo().phone())
                .createdAt(user.getCreatedAt())
                .build();
    }
}

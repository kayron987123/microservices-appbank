package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.gad.appbank.userservice.application.ports.output.UserPersistencePort;
import pe.gad.appbank.userservice.domain.model.*;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.RoleEntity;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.UserEntity;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.UserRoleEntity;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.UserRoleId;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.mapper.UserMapper;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.repository.RoleRepository;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.repository.UserDocumentRepository;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.repository.UserRepository;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.repository.UserRoleRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final UserDocumentRepository userDocumentRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        UserEntity entity = userMapper.toEntity(user);

        if (entity.getDocuments() != null) {
            entity.getDocuments().forEach(doc -> doc.setUser(entity));
        }

        UserEntity savedUserEntity = userRepository.save(entity);

        saveRolesManually(user, savedUserEntity);
        return userMapper.toDomain(savedUserEntity);
    }

    @Override
    public Optional<User> findUserById(UserId id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(Email email) {
        return Optional.empty();
    }

    @Override
    public boolean existsUserByEmail(Email email) {
        return userRepository.existsByEmail(email.value());
    }

    @Override
    public boolean existsUserByDocumentNumber(String documentNumber) {
        return userRepository.existsByDocumentNumber(documentNumber);
    }

    private void saveRolesManually(User user, UserEntity userEntity) {
        for (Role domainRole : user.getRoles()) {
            RoleEntity roleEntity = roleRepository.findByName(domainRole.name())
                    .orElseThrow();

            UserRoleId id = new UserRoleId();
            id.setUserId(userEntity.getId());
            id.setRoleId(roleEntity.getId());

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setId(id);
            userRole.setUser(userEntity);
            userRole.setRole(roleEntity);

            userRoleRepository.save(userRole);
        }
    }
}

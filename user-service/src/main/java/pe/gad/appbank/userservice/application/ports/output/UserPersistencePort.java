package pe.gad.appbank.userservice.application.ports.output;

import pe.gad.appbank.userservice.domain.model.Email;
import pe.gad.appbank.userservice.domain.model.User;
import pe.gad.appbank.userservice.domain.model.UserId;

import java.util.Optional;

public interface UserPersistencePort {
    User saveUser(User user);
    Optional<User> findUserById(UserId id);
    Optional<User> findUserByEmail(Email email);
    boolean existsUserByEmail(Email email);
    boolean existsUserByDocumentNumber(String documentNumber);
}

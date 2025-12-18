package pe.gad.appbank.userservice.application.ports.output;

import pe.gad.appbank.userservice.domain.model.Role;

import java.util.Optional;

public interface RolePersistencePort {
    Optional<Role> findRoleByName(String roleName);
}

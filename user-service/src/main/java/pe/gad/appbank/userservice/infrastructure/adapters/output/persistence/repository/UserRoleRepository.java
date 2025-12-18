package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.UserRoleEntity;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.UserRoleId;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleId> {
}

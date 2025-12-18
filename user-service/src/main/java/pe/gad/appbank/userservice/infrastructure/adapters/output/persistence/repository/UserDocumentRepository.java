package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gad.appbank.userservice.infrastructure.adapters.output.persistence.entities.UserDocumentEntity;

import java.util.UUID;

public interface UserDocumentRepository extends JpaRepository<UserDocumentEntity, UUID> {
}

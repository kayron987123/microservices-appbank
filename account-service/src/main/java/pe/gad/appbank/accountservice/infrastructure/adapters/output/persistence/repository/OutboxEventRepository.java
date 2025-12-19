package pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence.entities.OutboxEventEntity;

import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEventEntity, UUID> {
}

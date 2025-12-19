package pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.entities.CardTypeEntity;

import java.util.Optional;
import java.util.UUID;

public interface CardTypeRepository extends JpaRepository<CardTypeEntity, UUID> {
    Optional<CardTypeEntity> findByName(String name);
}

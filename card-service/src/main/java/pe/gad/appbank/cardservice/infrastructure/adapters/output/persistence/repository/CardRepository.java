package pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.entities.CardEntity;

import java.util.UUID;

public interface CardRepository extends JpaRepository<CardEntity, UUID> {
    Boolean existsByCardNumber(String cardNumber);
}

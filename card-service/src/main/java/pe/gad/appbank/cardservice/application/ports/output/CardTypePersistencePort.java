package pe.gad.appbank.cardservice.application.ports.output;

import pe.gad.appbank.cardservice.domain.model.CardType;

import java.util.Optional;

public interface CardTypePersistencePort {
    Optional<CardType> findCardTypeByName(String name);
}

package pe.gad.appbank.cardservice.application.ports.output;

import pe.gad.appbank.cardservice.domain.model.Card;
import pe.gad.appbank.cardservice.domain.model.CardType;

import java.util.Optional;

public interface CardPersistencePort {
    Card saveCard(Card card);
    Optional<CardType> findCardTypeByName(String name);
}

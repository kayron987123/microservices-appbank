package pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gad.appbank.cardservice.application.ports.output.CardPersistencePort;
import pe.gad.appbank.cardservice.domain.model.Card;
import pe.gad.appbank.cardservice.domain.model.CardType;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.entities.CardEntity;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.mapper.CardMapper;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.repository.CardRepository;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.repository.CardTypeRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardPersistenceAdapter implements CardPersistencePort {
    private final CardRepository cardRepository;
    private final CardTypeRepository cardTypeRepository;
    private final CardMapper cardMapper;

    @Override
    public Card saveCard(Card card) {
        CardEntity entity = cardMapper.toEntity(card);
        CardEntity savedEntity = cardRepository.save(entity);

        return cardMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<CardType> findCardTypeByName(String name) {
        return null;
    }
}

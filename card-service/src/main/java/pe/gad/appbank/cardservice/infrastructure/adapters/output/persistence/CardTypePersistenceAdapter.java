package pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.gad.appbank.cardservice.application.ports.output.CardTypePersistencePort;
import pe.gad.appbank.cardservice.domain.model.CardType;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.entities.CardTypeEntity;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.mapper.CardTypeMapper;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.repository.CardTypeRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CardTypePersistenceAdapter implements CardTypePersistencePort {
    private final CardTypeRepository cardTypeRepository;
    private final CardTypeMapper cardTypeMapper;

    @Override
    public Optional<CardType> findCardTypeByName(String name) {
        CardTypeEntity cardTypeEntity = cardTypeRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Card type not found"));
        return Optional.of(cardTypeMapper.toDomain(cardTypeEntity));
    }
}

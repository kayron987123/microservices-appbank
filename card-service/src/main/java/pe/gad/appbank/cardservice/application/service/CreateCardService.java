package pe.gad.appbank.cardservice.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gad.appbank.cardservice.application.ports.dto.CardDto;
import pe.gad.appbank.cardservice.application.ports.dto.CreateCardCommand;
import pe.gad.appbank.cardservice.application.ports.input.CreateCardUseCase;
import pe.gad.appbank.cardservice.application.ports.output.CardPersistencePort;
import pe.gad.appbank.cardservice.application.ports.output.CardTypePersistencePort;
import pe.gad.appbank.cardservice.application.ports.output.OutboxPersistencePort;
import pe.gad.appbank.cardservice.domain.model.*;

import java.security.SecureRandom;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateCardService implements CreateCardUseCase {
    private final CardPersistencePort cardPersistencePort;
    private final CardTypePersistencePort cardTypePersistencePort;
    private final OutboxPersistencePort outboxPersistencePort;
    private final PasswordHasher passwordHasher;

    @Override
    @Transactional
    public CardDto createCard(CreateCardCommand command) {
        CardType cardType = cardTypePersistencePort.findCardTypeByName(command.cardTypeName())
                .orElseThrow(() -> new IllegalArgumentException("Card type not found"));

        String rawCvv = generateRandomCvv();

        Card card = Card.generate(
                AccountId.of(command.accountId()),
                UserId.of(command.userId()),
                command.ownerName(),
                cardType,
                rawCvv,
                passwordHasher
        );

        Card savedCard = cardPersistencePort.saveCard(card);

        List<Object> events = card.pullDomainEvents();
        outboxPersistencePort.saveAll(events);

        return mapToDto(savedCard, rawCvv);
    }

    private String generateRandomCvv() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(1000);
        return String.format("%03d", num);
    }

    private CardDto mapToDto(Card card, String rawCvv) {
        return new CardDto(
                card.getId().toString(),
                card.getCardNumber().mask(),
                card.getCardHolderName(),
                card.getExpiry().month() + "/" + card.getExpiry().year(),
                rawCvv,
                card.getStatus().name()
        );
    }
}

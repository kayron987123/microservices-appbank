package pe.gad.appbank.cardservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import pe.gad.appbank.cardservice.domain.enums.CardTransactionStatus;
import pe.gad.appbank.cardservice.domain.enums.CardTransactionType;

import java.time.LocalDateTime;

@Getter
@Builder
public class CardTransaction {
    private final CardTransactionId id;
    private final CardId cardId;
    private final AccountId accountId;
    private final Money amount;
    private final String merchantName;
    private final CardTransactionType type;
    private final CardTransactionStatus status;
    private final LocalDateTime createdAt;
}

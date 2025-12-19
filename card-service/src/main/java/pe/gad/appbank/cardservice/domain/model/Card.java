package pe.gad.appbank.cardservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import pe.gad.appbank.cardservice.domain.enums.CardStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Card extends AggregateRoot{
    private final CardId id;
    private final AccountId accountId;
    private final UserId userId;
    private final CardType cardType;
    private final CardNumber cardNumber;
    private final String cardHolderName;
    private final CardExpiry expiry;
    private final String cvvHash;
    private CardStatus status;
    private Money dailyLimit;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime cancelledAt;

    @Builder
    private Card(CardId id, AccountId accountId, UserId userId, CardType cardType,
                 CardNumber cardNumber, String cardHolderName, CardExpiry expiry,
                 String cvvHash, CardStatus status, Money dailyLimit, LocalDateTime createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.userId = userId;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiry = expiry;
        this.cvvHash = cvvHash;
        this.status = status;
        this.dailyLimit = dailyLimit;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }

    public static Card generate(AccountId accountId, UserId userId, String ownerName,
                                CardType cardType, String rawCvv, PasswordHasher hasher) {

        if (!cardType.isActive()) {
            throw new IllegalArgumentException("Card type is not active");
        }

        String hashedCvv = hasher.hash(rawCvv);

        return new Card(
                new CardId(UUID.randomUUID()),
                accountId,
                userId,
                cardType,
                CardNumber.generate(cardType.getNetwork()),
                ownerName.toUpperCase(),
                CardExpiry.fiveYearsFromNow(),
                hashedCvv,
                CardStatus.ACTIVE,
                Money.of(BigDecimal.valueOf(00.00)),
                LocalDateTime.now()
        );
    }

    public void block() {
        if (this.status == CardStatus.CANCELLED) {
            throw new IllegalStateException("Cannot block a cancelled card");
        }
        this.status = CardStatus.BLOCKED;
        this.updatedAt = LocalDateTime.now();
    }

    public void authorizePurchase(Money amount) {
        if (this.status != CardStatus.ACTIVE) {
            throw new IllegalStateException("Card is not active");
        }

        if (amount.amount().compareTo(this.dailyLimit.amount()) > 0) {
            throw new IllegalArgumentException("Transaction exceeds daily limit");
        }
    }
}

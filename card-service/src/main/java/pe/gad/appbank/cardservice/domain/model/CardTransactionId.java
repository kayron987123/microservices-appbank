package pe.gad.appbank.cardservice.domain.model;

import java.util.UUID;

public record CardTransactionId(UUID value) {
    public CardTransactionId {
        if (value == null) {
            throw new IllegalArgumentException("CardTransactionId value cannot be null");
        }
    }

    public static CardTransactionId generate() {
        return new CardTransactionId(UUID.randomUUID());
    }

    public static CardTransactionId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("CardTransactionId string cannot be null or empty");
        }
        return new CardTransactionId(UUID.fromString(value));
    }

    public static CardTransactionId of(UUID value) {
        return new CardTransactionId(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

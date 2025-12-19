package pe.gad.appbank.cardservice.domain.model;

import java.util.UUID;

public record CardId(UUID value) {
    public CardId {
        if (value == null) {
            throw new IllegalArgumentException("CardId value cannot be null");
        }
    }

    public static CardId generate() {
        return new CardId(UUID.randomUUID());
    }

    public static CardId of(UUID value) {
        return new CardId(value);
    }

    public static CardId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("CardId string cannot be null or empty");
        }
        return new CardId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

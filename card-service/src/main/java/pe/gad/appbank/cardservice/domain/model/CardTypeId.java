package pe.gad.appbank.cardservice.domain.model;

import java.util.UUID;

public record CardTypeId(UUID value) {
    public CardTypeId {
        if (value == null) {
            throw new IllegalArgumentException("CardTypeId value cannot be null");
        }
    }

    public static CardTypeId of(String value) {
        return new CardTypeId(UUID.fromString(value));
    }

    public static CardTypeId of(UUID value) {
        return new CardTypeId(value);
    }
}

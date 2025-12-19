package pe.gad.appbank.cardservice.domain.model;

import java.util.UUID;

public record AccountId(UUID value) {
    public AccountId {
        if (value == null) {
            throw new IllegalArgumentException("AccountId value cannot be null");
        }
    }

    public static AccountId of(UUID value) {
        return new AccountId(value);
    }

    public static AccountId of(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("AccountId string cannot be null or empty");
        }
        return new AccountId(UUID.fromString(value));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

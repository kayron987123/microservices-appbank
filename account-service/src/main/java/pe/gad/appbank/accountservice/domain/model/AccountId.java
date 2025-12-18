package pe.gad.appbank.accountservice.domain.model;

import java.util.UUID;

public record AccountId(UUID value) {
    public AccountId {
        if (value == null) {
            throw new IllegalArgumentException("AccountId value cannot be null");
        }
    }

    public static AccountId generate() {
        return new AccountId(UUID.randomUUID());
    }

    public static AccountId of(String uuid) {
        return new AccountId(UUID.fromString(uuid));
    }

    public static AccountId of(UUID uuid) {
        return new AccountId(uuid);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

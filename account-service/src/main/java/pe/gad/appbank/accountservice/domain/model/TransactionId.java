package pe.gad.appbank.accountservice.domain.model;

import org.springframework.util.StringUtils;

import java.util.UUID;

public record TransactionId(UUID value) {
    public TransactionId {
        if (value == null) {
            throw new IllegalArgumentException("TransactionId value cannot be null");
        }
    }

    public static TransactionId generate() {
        return new TransactionId(UUID.randomUUID());
    }

    public static TransactionId of(String uuid) {
        if (!StringUtils.hasText(uuid)) {
            throw new IllegalArgumentException("TransactionId string cannot be null or empty");
        }
        return new TransactionId(UUID.fromString(uuid));
    }

    public static TransactionId of(UUID uuid) {
        return new TransactionId(uuid);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

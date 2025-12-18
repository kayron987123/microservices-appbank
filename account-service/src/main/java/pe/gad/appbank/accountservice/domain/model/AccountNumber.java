package pe.gad.appbank.accountservice.domain.model;

import org.springframework.util.StringUtils;

import java.util.concurrent.ThreadLocalRandom;

public record AccountNumber(String value) {
    public AccountNumber {
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }

        if (value.length() > 20) {
            throw new IllegalArgumentException("Account number is too long");
        }

        if (!value.matches("\\d+")) {
            throw new IllegalArgumentException("Account number must contain only digits");
        }
    }

    public static AccountNumber of(String value) {
        return new AccountNumber(value);
    }

    public static AccountNumber generate() {
        long randomNum = ThreadLocalRandom.current().nextLong(100_000_000_000L, 999_999_999_999L);
        return new AccountNumber(String.valueOf(randomNum));
    }

    @Override
    public String toString() {
        return value;
    }
}

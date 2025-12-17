package pe.gad.appbank.userservice.domain.model;

import org.springframework.util.StringUtils;
import pe.gad.appbank.userservice.domain.exception.InvalidPasswordException;

public record Password(String value, boolean isEncoded) {
    public Password {
        if (!StringUtils.hasText(value)) {
            throw new InvalidPasswordException("Password cannot be empty");
        }

        if (!isEncoded) {
            validateRawPassword(value);
        }
    }

    public static Password raw(String rawPassword) {
        return new Password(rawPassword, false);
    }

    public static Password encoded(String encodedPassword) {
        return new Password(encodedPassword, true);
    }

    private static void validateRawPassword(String password) {
        if (password.length() < 8) {
            throw new InvalidPasswordException("Password must be at least 8 characters");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("Password must contain uppercase letter");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new InvalidPasswordException("Password must contain lowercase letter");
        }
        if (!password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("Password must contain number");
        }
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new InvalidPasswordException("Password must contain special character");
        }
    }

    @Override
    public String toString() {
        return "Password[******]";
    }
}

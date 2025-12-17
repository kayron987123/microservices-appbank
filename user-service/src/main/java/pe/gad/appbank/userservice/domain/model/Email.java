package pe.gad.appbank.userservice.domain.model;

import org.springframework.util.StringUtils;
import pe.gad.appbank.userservice.domain.exception.InvalidEmailException;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Email {
        if (!StringUtils.hasText(value)) {
            throw new InvalidEmailException("Email cannot be empty");
        }

        value = value.toLowerCase().trim();

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new InvalidEmailException("Invalid email format: " + value);
        }
    }

    public static Email of(String value) {
        return new Email(value);
    }

    public String getDomain() {
        return value.substring(value.indexOf('@') + 1);
    }

    public boolean isFromDomain(String domain) {
        return getDomain().equalsIgnoreCase(domain);
    }
}

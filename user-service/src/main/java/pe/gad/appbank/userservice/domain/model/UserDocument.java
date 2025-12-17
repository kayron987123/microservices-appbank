package pe.gad.appbank.userservice.domain.model;

import lombok.Builder;
import org.springframework.util.StringUtils;
import pe.gad.appbank.userservice.domain.enums.DocumentType;

import java.time.LocalDate;

@Builder
public record UserDocument(DocumentType type, String number, LocalDate issuedDate, LocalDate expiryDate) {
    public UserDocument {
        if (type == null) {
            throw new IllegalArgumentException("Document type is required");
        }
        if (!StringUtils.hasText(number)) {
            throw new IllegalArgumentException("Document number is required");
        }
        if (issuedDate == null || expiryDate == null) {
            throw new IllegalArgumentException("Dates are required");
        }
        if (issuedDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Issued date cannot be in the future");
        }
        if (expiryDate.isBefore(issuedDate)) {
            throw new IllegalArgumentException("Expiry date must be after issued date");
        }

        number = number.trim().toUpperCase();
    }

    public static UserDocument of(
            DocumentType type,
            String number,
            LocalDate issuedDate,
            LocalDate expiryDate
    ) {
        return new UserDocument(type, number, issuedDate, expiryDate);
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    public boolean isValid() {
        return !isExpired() && LocalDate.now().isAfter(issuedDate);
    }

    public long daysUntilExpiry() {
        return LocalDate.now().until(expiryDate).getDays();
    }
}

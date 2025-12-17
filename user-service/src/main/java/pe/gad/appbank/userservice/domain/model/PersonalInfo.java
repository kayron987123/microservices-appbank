package pe.gad.appbank.userservice.domain.model;

import lombok.Builder;
import org.springframework.util.StringUtils;
import pe.gad.appbank.userservice.domain.exception.InvalidUserException;

@Builder
public record PersonalInfo(String firstName, String lastName, String phone, String documentNumber) {
    public PersonalInfo {
        if (!StringUtils.hasText(firstName)) {
            throw new InvalidUserException("First name is required");
        }
        if (!StringUtils.hasText(lastName)) {
            throw new InvalidUserException("Last name is required");
        }
        if (!StringUtils.hasText(phone) || !phone.matches("\\+?\\d{9,15}")) {
            throw new InvalidUserException("Invalid phone number format");
        }
        if (!StringUtils.hasText(documentNumber)) {
            throw new InvalidUserException("Document number is required");
        }

        firstName = firstName.trim();
        lastName = lastName.trim();
        phone = phone.trim();
        documentNumber = documentNumber.trim().toUpperCase();
    }

    public static PersonalInfo of(
            String firstName,
            String lastName,
            String phone,
            String documentNumber
    ) {
        return new PersonalInfo(firstName, lastName, phone, documentNumber);
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getInitials() {
        return firstName.charAt(0) + "" + lastName.charAt(0);
    }
}

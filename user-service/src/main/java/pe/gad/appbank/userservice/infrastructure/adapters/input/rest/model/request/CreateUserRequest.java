package pe.gad.appbank.userservice.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateUserRequest(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Phone is required")
        String phone,

        @NotBlank(message = "Document number is required")
        String documentNumber,

        @NotBlank(message = "Document type is required")
        String documentType,

        @NotNull(message = "Document issued date is required")
        @PastOrPresent(message = "Document issued date must be in the past or present")
        LocalDate documentIssuedDate,

        @NotNull(message = "Document issued place is required")
        @Future(message = "Document expiry date must be in the future")
        LocalDate documentExpiryDate
) {
}

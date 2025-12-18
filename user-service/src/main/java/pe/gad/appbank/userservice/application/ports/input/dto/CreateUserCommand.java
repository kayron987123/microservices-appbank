package pe.gad.appbank.userservice.application.ports.input.dto;

import java.time.LocalDate;

public record CreateUserCommand(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        String documentNumber,
        String documentType,
        LocalDate documentIssuedDate,
        LocalDate documentExpiryDate
) {
}

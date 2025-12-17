package pe.gad.appbank.userservice.application.ports.input.dto;

public record CreateUserCommand(
        String email,
        String password,
        String firstName,
        String lastName,
        String phone,
        String documentNumber
) {
}

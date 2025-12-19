package pe.gad.appbank.accountservice.application.ports.input.dto;

public record CreateAccountCommand(
        String userId,
        String accountType,
        String currency
) {
}

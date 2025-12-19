package pe.gad.appbank.accountservice.infrastructure.adapters.input.rest.model.request;

public record CreateAccountRequest(
        String userId,
        String accountType,
        String currency
) {
}

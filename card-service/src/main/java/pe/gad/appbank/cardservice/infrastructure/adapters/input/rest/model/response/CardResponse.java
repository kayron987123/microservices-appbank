package pe.gad.appbank.cardservice.infrastructure.adapters.input.rest.model.response;

public record CardResponse(
        String id,
        String cardNumber,
        String cardHolderName,
        String expiry,
        String cvv,
        String status
) {
}

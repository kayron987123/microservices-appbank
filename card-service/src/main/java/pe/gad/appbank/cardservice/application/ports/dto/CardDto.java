package pe.gad.appbank.cardservice.application.ports.dto;

public record CardDto(
        String id,
        String cardNumber,
        String cardHolderName,
        String expiry,
        String cvv,
        String status
) {
}

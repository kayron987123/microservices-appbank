package pe.gad.appbank.cardservice.application.ports.dto;

public record CreateCardCommand(
        String accountId,
        String userId,
        String ownerName,
        String cardTypeName
) {
}

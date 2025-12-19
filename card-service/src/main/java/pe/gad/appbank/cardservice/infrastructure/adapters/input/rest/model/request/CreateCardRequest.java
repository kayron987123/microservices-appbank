package pe.gad.appbank.cardservice.infrastructure.adapters.input.rest.model.request;

import jakarta.validation.constraints.NotBlank;

public record CreateCardRequest(
        @NotBlank(message = "Account ID is required")
        String accountId,

        @NotBlank(message = "User ID is required")
        String userId,

        @NotBlank(message = "Owner name is required")
        String ownerName,

        @NotBlank(message = "Card Type is required")
        String cardType
) {
}

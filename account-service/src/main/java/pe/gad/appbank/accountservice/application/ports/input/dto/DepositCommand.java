package pe.gad.appbank.accountservice.application.ports.input.dto;

import java.math.BigDecimal;

public record DepositCommand(
        String accountId,
        BigDecimal amount,
        String currency,
        String description,
        String origin
) {
}

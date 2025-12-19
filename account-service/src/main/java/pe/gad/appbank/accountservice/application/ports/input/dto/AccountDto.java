package pe.gad.appbank.accountservice.application.ports.input.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record AccountDto(
        String id,
        String userId,
        String accountNumber,
        String type,
        BigDecimal balance,
        String currency,
        LocalDateTime createdAt
) {
}

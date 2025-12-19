package pe.gad.appbank.accountservice.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(
        String id,
        String userId,
        String accountNumber,
        String type,
        BigDecimal balance,
        String currency,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
        LocalDateTime createdAt
) {
}

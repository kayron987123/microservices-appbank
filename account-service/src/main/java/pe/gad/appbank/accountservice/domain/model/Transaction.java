package pe.gad.appbank.accountservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pe.gad.appbank.accountservice.domain.enums.TransactionType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
public class Transaction {
    private final TransactionId id;
    private final TransactionType type;
    private final Money amount;
    private final Money balanceAfter;
    private final String description;
    private final String originService;
    private final LocalDateTime createdAt;

    public static Transaction create(TransactionType type, Money amount, Money balanceAfter, String description, String origin) {
        return Transaction.builder()
                .id(new TransactionId(UUID.randomUUID()))
                .type(type)
                .amount(amount)
                .balanceAfter(balanceAfter)
                .description(description)
                .originService(origin)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

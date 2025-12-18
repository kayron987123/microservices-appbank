package pe.gad.appbank.accountservice.domain.model.event;

import java.time.LocalDateTime;

public record AccountCreatedEvent(
        String accountId,
        String userId,
        String accountNumber,
        String accountType,
        LocalDateTime occurredOn
) implements DomainEvent {
    public static final String AGGREGATE_TYPE = "ACCOUNT";

    @Override
    public String aggregateId() {
        return accountId;
    }

    @Override
    public String aggregateType() {
        return AGGREGATE_TYPE;
    }
}

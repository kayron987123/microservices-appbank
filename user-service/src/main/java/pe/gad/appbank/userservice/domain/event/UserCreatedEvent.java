package pe.gad.appbank.userservice.domain.event;

import java.time.LocalDateTime;

public record UserCreatedEvent(
        String userId,
        String email,
        String firstName,
        String lastName,
        String documentNumber,
        LocalDateTime occurredOn
) implements DomainEvent {
    public static UserCreatedEvent from(String userId, String email, String firstName, String lastName, String documentNumber) {
        return new UserCreatedEvent(userId, email, firstName, lastName, documentNumber, LocalDateTime.now());
    }

    public static final String AGGREGATE_TYPE = "USER";

    @Override
    public String aggregateId() {
        return this.userId;
    }


    @Override
    public String aggregateType() {
        return AGGREGATE_TYPE;
    }
}

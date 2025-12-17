package pe.gad.appbank.userservice.domain.event;

import java.time.LocalDateTime;

public record UserCreatedEvent(
        String userId,
        String email,
        String firstName,
        String lastName,
        String documentNumber,
        LocalDateTime occurredOn
) {
    public static UserCreatedEvent from(String userId, String email, String firstName, String lastName, String documentNumber) {
        return new UserCreatedEvent(userId, email, firstName, lastName, documentNumber, LocalDateTime.now());
    }
}

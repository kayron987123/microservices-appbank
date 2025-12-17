package pe.gad.appbank.userservice.domain.exception;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String userId) {
        super("User not found: " + userId);
    }
}

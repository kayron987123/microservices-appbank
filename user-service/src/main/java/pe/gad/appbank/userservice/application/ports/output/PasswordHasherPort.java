package pe.gad.appbank.userservice.application.ports.output;

public interface PasswordHasherPort {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}

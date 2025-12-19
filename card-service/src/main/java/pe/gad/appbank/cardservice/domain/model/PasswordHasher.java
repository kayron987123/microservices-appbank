package pe.gad.appbank.cardservice.domain.model;

public interface PasswordHasher {
    String hash(String raw);
    boolean matches(String raw, String hashed);
}

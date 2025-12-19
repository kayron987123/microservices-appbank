package pe.gad.appbank.cardservice.infrastructure.adapters.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.gad.appbank.cardservice.domain.model.PasswordHasher;

@Component
@RequiredArgsConstructor
public class BCryptHasher implements PasswordHasher {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String hash(String raw) {
        return passwordEncoder.encode(raw);
    }

    @Override
    public boolean matches(String raw, String hashed) {
        return passwordEncoder.matches(raw, hashed);
    }
}

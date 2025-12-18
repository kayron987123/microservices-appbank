package pe.gad.appbank.userservice.infrastructure.adapters.output.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.gad.appbank.userservice.application.ports.output.PasswordHasherPort;

@Component
@RequiredArgsConstructor
public class BcryptPasswordAdapter implements PasswordHasherPort {
    private final PasswordEncoder passwordEncoder;

    @Override
    public String encode(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

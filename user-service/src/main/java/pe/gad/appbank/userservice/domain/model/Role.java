package pe.gad.appbank.userservice.domain.model;

import lombok.Builder;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Builder
public record Role(UUID id, String name, String description) {
    public static Role create(String name, String description) {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Role name is required");
        }

        return Role.builder()
                .id(UUID.randomUUID())
                .name(name.toUpperCase())
                .description(description)
                .build();
    }

    public static Role of(UUID id, String name, String description) {
        return Role.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
    }
}

package pe.gad.appbank.userservice.application.ports.input.dto;

import java.time.LocalDateTime;

public record UserDto(
        String id,
        String completeName,
        String email,
        String phone,
        LocalDateTime createdAt
) {
}

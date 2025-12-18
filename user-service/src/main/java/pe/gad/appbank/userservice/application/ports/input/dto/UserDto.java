package pe.gad.appbank.userservice.application.ports.input.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(
        String id,
        String completeName,
        String email,
        String phone,
        String documentNumber,
        LocalDateTime createdAt
) {
}

package pe.gad.appbank.userservice.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record UserResponse(
        String id,
        String completeName,
        String email,
        String phone,
        String documentNumber,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
        LocalDateTime createdAt
) {
}

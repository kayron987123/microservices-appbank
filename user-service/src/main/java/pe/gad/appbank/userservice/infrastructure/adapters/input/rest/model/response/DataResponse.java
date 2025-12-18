package pe.gad.appbank.userservice.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record DataResponse<T>(
        int status,
        String message,
        T data,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Lima")
        LocalDateTime timestamp
) {
}

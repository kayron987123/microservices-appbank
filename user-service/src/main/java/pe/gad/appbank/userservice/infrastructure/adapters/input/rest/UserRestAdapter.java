package pe.gad.appbank.userservice.infrastructure.adapters.input.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gad.appbank.userservice.application.ports.input.CreateUserUseCase;
import pe.gad.appbank.userservice.application.ports.input.dto.CreateUserCommand;
import pe.gad.appbank.userservice.application.ports.input.dto.UserDto;
import pe.gad.appbank.userservice.infrastructure.adapters.input.rest.mapper.UserRestMapper;
import pe.gad.appbank.userservice.infrastructure.adapters.input.rest.model.request.CreateUserRequest;
import pe.gad.appbank.userservice.infrastructure.adapters.input.rest.model.response.DataResponse;
import pe.gad.appbank.userservice.infrastructure.adapters.input.rest.model.response.UserResponse;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestAdapter {
    private final CreateUserUseCase createUserUseCase;
    private final UserRestMapper userRestMapper;

    @PostMapping
    public ResponseEntity<DataResponse<UserResponse>> createUser(@RequestBody @Valid CreateUserRequest request) {
        CreateUserCommand command = userRestMapper.toCommand(request);

        UserDto userCreated = createUserUseCase.createUser(command);
        UserResponse userResponse = userRestMapper.toResponse(userCreated);

        URI location = URI.create("/users/" + userResponse.id());
        DataResponse<UserResponse> responseBody = DataResponse.<UserResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(userResponse)
                .timestamp(LocalDateTime.now())
                .build();

        return  ResponseEntity.created(location).body(responseBody);
    }
}

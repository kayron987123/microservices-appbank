package pe.gad.appbank.userservice.application.ports.input;

import pe.gad.appbank.userservice.application.ports.input.dto.CreateUserCommand;
import pe.gad.appbank.userservice.application.ports.input.dto.UserDto;

public interface CreateUserUseCase {
    UserDto createUser(CreateUserCommand command);
}

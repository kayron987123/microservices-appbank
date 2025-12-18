package pe.gad.appbank.userservice.infrastructure.adapters.input.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.gad.appbank.userservice.application.ports.input.dto.CreateUserCommand;
import pe.gad.appbank.userservice.application.ports.input.dto.UserDto;
import pe.gad.appbank.userservice.infrastructure.adapters.input.rest.model.request.CreateUserRequest;
import pe.gad.appbank.userservice.infrastructure.adapters.input.rest.model.response.UserResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface UserRestMapper {

    CreateUserCommand toCommand(CreateUserRequest request);

    UserResponse toResponse(UserDto userCreated);
}

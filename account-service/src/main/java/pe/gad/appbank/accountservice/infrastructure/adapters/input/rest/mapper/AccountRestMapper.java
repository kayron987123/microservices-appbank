package pe.gad.appbank.accountservice.infrastructure.adapters.input.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.gad.appbank.accountservice.application.ports.input.dto.AccountDto;
import pe.gad.appbank.accountservice.application.ports.input.dto.CreateAccountCommand;
import pe.gad.appbank.accountservice.infrastructure.adapters.input.rest.model.request.CreateAccountRequest;
import pe.gad.appbank.accountservice.infrastructure.adapters.input.rest.model.response.AccountResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface AccountRestMapper {

    CreateAccountCommand toCommand(CreateAccountRequest request);

    AccountResponse toResponse(AccountDto accountDto);
}

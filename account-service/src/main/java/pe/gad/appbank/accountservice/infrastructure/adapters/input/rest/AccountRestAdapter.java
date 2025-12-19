package pe.gad.appbank.accountservice.infrastructure.adapters.input.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.gad.appbank.accountservice.application.ports.input.CreateAccountUseCase;
import pe.gad.appbank.accountservice.application.ports.input.dto.AccountDto;
import pe.gad.appbank.accountservice.infrastructure.adapters.input.rest.mapper.AccountRestMapper;
import pe.gad.appbank.accountservice.infrastructure.adapters.input.rest.model.request.CreateAccountRequest;
import pe.gad.appbank.accountservice.infrastructure.adapters.input.rest.model.response.AccountResponse;
import pe.gad.appbank.accountservice.infrastructure.adapters.input.rest.model.response.DataResponse;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
@Slf4j
public class AccountRestAdapter {
    private final CreateAccountUseCase createAccountUseCase;
    private final AccountRestMapper accountRestMapper;

    @PostMapping
    public ResponseEntity<DataResponse<AccountResponse>> createAccount(@RequestBody @Valid CreateAccountRequest request) {
        AccountDto accountDto = createAccountUseCase.createAccount(accountRestMapper.toCommand(request));

        AccountResponse response = accountRestMapper.toResponse(accountDto);

        URI location = URI.create("/accounts/" + response.id());
        DataResponse<AccountResponse> dataResponse = DataResponse.<AccountResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Account created successfully")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.created(location).body(dataResponse);
    }
}

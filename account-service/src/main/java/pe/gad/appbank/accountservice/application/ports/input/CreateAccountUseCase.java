package pe.gad.appbank.accountservice.application.ports.input;

import pe.gad.appbank.accountservice.application.ports.input.dto.AccountDto;
import pe.gad.appbank.accountservice.application.ports.input.dto.CreateAccountCommand;

public interface CreateAccountUseCase {
    AccountDto createAccount(CreateAccountCommand command);
}

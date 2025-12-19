package pe.gad.appbank.accountservice.application.ports.output;

import pe.gad.appbank.accountservice.domain.model.Account;
import pe.gad.appbank.accountservice.domain.model.AccountId;
import pe.gad.appbank.accountservice.domain.model.AccountNumber;
import pe.gad.appbank.accountservice.domain.model.UserId;

import java.util.Optional;

public interface AccountPersistencePort {
    Account save(Account account);
    Optional<Account> findAccountById(AccountId id);
    Optional<Account> findAccountByUserId(UserId userId);
    boolean existsByAccountNumber(AccountNumber accountNumber);
}

package pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.gad.appbank.accountservice.application.ports.output.AccountPersistencePort;
import pe.gad.appbank.accountservice.domain.model.Account;
import pe.gad.appbank.accountservice.domain.model.AccountId;
import pe.gad.appbank.accountservice.domain.model.AccountNumber;
import pe.gad.appbank.accountservice.domain.model.UserId;
import pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence.entities.AccountEntity;
import pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence.mapper.AccountMapper;
import pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence.repository.AccountRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AccountPersistenceAdapter implements AccountPersistencePort {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public Account save(Account account) {
        log.info("Account to save: {}", account);
        AccountEntity entity = accountMapper.toEntity(account);
        AccountEntity savedEntity = accountRepository.save(entity);
        return accountMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Account> findAccountById(AccountId id) {
        return Optional.empty();
    }

    @Override
    public Optional<Account> findAccountByUserId(UserId userId) {
        return Optional.empty();
    }

    @Override
    public boolean existsByAccountNumber(AccountNumber accountNumber) {
        return accountRepository.existsByAccountNumber(accountNumber.value());
    }
}

package pe.gad.appbank.accountservice.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.gad.appbank.accountservice.application.ports.input.CreateAccountUseCase;
import pe.gad.appbank.accountservice.application.ports.input.dto.AccountDto;
import pe.gad.appbank.accountservice.application.ports.input.dto.CreateAccountCommand;
import pe.gad.appbank.accountservice.application.ports.output.AccountPersistencePort;
import pe.gad.appbank.accountservice.application.ports.output.OutboxPersistencePort;
import pe.gad.appbank.accountservice.domain.enums.AccountType;
import pe.gad.appbank.accountservice.domain.model.Account;
import pe.gad.appbank.accountservice.domain.model.AccountNumber;
import pe.gad.appbank.accountservice.domain.model.Money;
import pe.gad.appbank.accountservice.domain.model.UserId;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateAccountService implements CreateAccountUseCase {
    private final AccountPersistencePort accountPersistencePort;
    private final OutboxPersistencePort outboxPersistencePort;
    public static final BigDecimal BALANCE_DEFAULT = BigDecimal.ZERO;

    @Override
    @Transactional
    public AccountDto createAccount(CreateAccountCommand command) {
        UserId userId = UserId.of(command.userId());
        AccountType accountType = AccountType.valueOf(command.accountType());
        AccountNumber accountNumber = generateUniqueAccountNumber();

        Money money = Money.of(BALANCE_DEFAULT, command.currency());
        log.info("Money to open account: {}", money);

        Account account = Account.open(userId, accountType, accountNumber, money);

        log.info("Account opened: {}", account);

        Account savedAccount = accountPersistencePort.save(account);

        List<Object> events = account.pullDomainEvents();
        outboxPersistencePort.saveAll(events);

        return mapToDto(savedAccount);
    }

    private AccountDto mapToDto(Account account) {
        return AccountDto.builder()
                .id(account.getId().value().toString())
                .userId(account.getUserId().value().toString())
                .accountNumber(account.getAccountNumber().value())
                .type(account.getType().name())
                .balance(account.getBalance().amount())
                .currency(account.getBalance().currency())
                .createdAt(account.getCreatedAt())
                .build();
    }

    private AccountNumber generateUniqueAccountNumber() {
        int attempts = 0;
        final int MAX_ATTEMPTS = 5;

        while (attempts < MAX_ATTEMPTS) {
            AccountNumber candidate = AccountNumber.generate();
            if (!accountPersistencePort.existsByAccountNumber(candidate)) {
                return candidate;
            }
            attempts++;
        }

        throw new IllegalStateException("Failed to generate a unique account number after " + MAX_ATTEMPTS + " attempts");
    }
}

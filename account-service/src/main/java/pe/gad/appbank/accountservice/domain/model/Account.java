package pe.gad.appbank.accountservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pe.gad.appbank.accountservice.domain.enums.AccountStatus;
import pe.gad.appbank.accountservice.domain.enums.AccountType;
import pe.gad.appbank.accountservice.domain.enums.TransactionType;
import pe.gad.appbank.accountservice.domain.exception.InsufficientFundsException;
import pe.gad.appbank.accountservice.domain.model.event.AccountCreatedEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
public class Account extends AggregateRoot{
    private final AccountId id;
    private final UserId userId;
    private final AccountNumber accountNumber;
    private final AccountType type;
    private Money balance;
    private AccountStatus status;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
    private final List<Transaction> recentTransactions;

    @Builder
    private Account(AccountId id, UserId userId, AccountNumber accountNumber, AccountType type,
                    Money balance, AccountStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.recentTransactions = new ArrayList<>();
        this.version = 0L;
    }

    public static Account open(UserId userId, AccountType type, AccountNumber accountNumber, Money balance) {
        Account account = new Account(
                new AccountId(UUID.randomUUID()),
                userId,
                accountNumber,
                type,
                balance,
                AccountStatus.ACTIVE,
                LocalDateTime.now()
        );

        account.registerEvent(new AccountCreatedEvent(
                account.id.value().toString(),
                account.userId.value().toString(),
                account.accountNumber.value(),
                account.type.toString(),
                account.createdAt
        ));

        return account;
    }

    public Transaction deposit(Money amount, String description, String origin) {
        validateActive();
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        this.balance = this.balance.add(amount);
        this.updatedAt = LocalDateTime.now();

        Transaction tx = Transaction.create(
                TransactionType.DEPOSIT,
                amount,
                this.balance,
                description,
                origin
        );
        this.recentTransactions.add(tx);

        return tx;
    }

    public Transaction withdraw(Money amount, String description, String origin) {
        validateActive();
        if (!amount.isPositive()) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (!this.balance.isGreaterThanOrEqual(amount)) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }

        this.balance = this.balance.subtract(amount);
        this.updatedAt = LocalDateTime.now();

        Transaction tx = Transaction.create(
                TransactionType.WITHDRAWAL,
                amount,
                this.balance,
                description,
                origin
        );
        this.recentTransactions.add(tx);

        return tx;
    }

    private void validateActive() {
        if (this.status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account is not active");
        }
    }

    public static Account withId(AccountId id, UserId userId, AccountNumber number, AccountType type,
                                 Money balance, AccountStatus status, Long version,
                                 LocalDateTime createdAt, LocalDateTime updatedAt) {
        Account acc = new Account(id, userId, number, type, balance, status, createdAt);
        acc.updatedAt = updatedAt;
        acc.version = version;
        return acc;
    }
}

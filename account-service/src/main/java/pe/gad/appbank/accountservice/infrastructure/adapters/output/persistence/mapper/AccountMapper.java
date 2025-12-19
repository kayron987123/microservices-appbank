package pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pe.gad.appbank.accountservice.domain.model.*;
import pe.gad.appbank.accountservice.infrastructure.adapters.output.persistence.entities.AccountEntity;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface AccountMapper {

    @Mapping(target = "id", source = "account.id.value")
    @Mapping(target = "userId", source = "account.userId.value")
    @Mapping(target = "accountNumber", source = "account.accountNumber.value")
    @Mapping(target = "accountType", source = "account.type")
    @Mapping(target = "balance", source = "account.balance.amount")
    @Mapping(target = "currency", source = "account.balance.currency")
    @Mapping(target = "version", ignore = true)
    AccountEntity toEntity(Account account);

    @Mapping(target = "id", source = "id", qualifiedByName = "mapAccountId")
    @Mapping(target = "userId", source = "userId", qualifiedByName = "mapUserId")
    @Mapping(target = "accountNumber", source = "accountNumber", qualifiedByName = "mapAccountNumber")
    @Mapping(target = "type", source = "accountType")
    @Mapping(target = "balance", source = "accountEntity", qualifiedByName = "mapMoney")
    Account toDomain(AccountEntity accountEntity);

    @Named("mapAccountId")
    default AccountId mapAccountId(UUID accountId) {
        return accountId != null ? AccountId.of(accountId) : null;
    }

    @Named("mapUserId")
    default UserId mapUserId(UUID id) {
        return id != null ? UserId.of(id) : null;
    }

    @Named("mapAccountNumber")
    default AccountNumber mapAccountNumber(String accountNumber) {
        return accountNumber != null ? AccountNumber.of(accountNumber) : null;
    }

    @Named("mapMoney")
    default Money mapMoney(AccountEntity entity) {
        return Money.of(entity.getBalance(), entity.getCurrency());
    }
}

package pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pe.gad.appbank.cardservice.domain.model.*;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.entities.CardEntity;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN
        , uses = {CardTypeMapper.class})
public interface CardMapper {
    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "accountId", source = "accountId.value")
    @Mapping(target = "userId", source = "userId.value")
    @Mapping(target = "cardNumber", source = "cardNumber.value")
    @Mapping(target = "expiryMonth", source = "expiry.month")
    @Mapping(target = "expiryYear", source = "expiry.year")
    @Mapping(target = "cvv", source = "cvvHash")
    @Mapping(target = "dailyLimit", source = "dailyLimit.amount")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    CardEntity toEntity(Card card);

    @Mapping(target = "id", source = "id", qualifiedByName = "mapCardId")
    @Mapping(target = "accountId", source = "accountId", qualifiedByName = "mapAccountId")
    @Mapping(target = "userId", source = "userId", qualifiedByName = "mapUserId")
    @Mapping(target = "cardNumber", source = "cardNumber", qualifiedByName = "mapCardNumber")
    @Mapping(target = "cvvHash", source = "cvv")
    @Mapping(target = "dailyLimit", source = "dailyLimit", qualifiedByName = "mapMoney")
    @Mapping(target = "expiry", source = "cardEntity", qualifiedByName = "mapCardExpiry")
    Card toDomain(CardEntity cardEntity);

    @Named("mapCardId")
    default CardId mapCardId(UUID id) {
        return id != null ? CardId.of(id) : null;
    }

    @Named("mapMoney")
    default Money mapMoney(BigDecimal amount) {
        return amount != null ? Money.of(amount) : null;
    }

    @Named("mapAccountId")
    default AccountId mapAccountId(UUID id) {
        return id != null ? AccountId.of(id) : null;
    }

    @Named("mapUserId")
    default UserId mapUserId(UUID id) {
        return id != null ? UserId.of(id) : null;
    }

    @Named("mapCardNumber")
    default CardNumber mapCardNumber(String cardNumber) {
        return cardNumber != null ? CardNumber.of(cardNumber) : null;
    }

    @Named("mapCardExpiry")
    default CardExpiry mapCardExpiry(CardEntity cardEntity) {
        return CardExpiry.of(cardEntity.getExpiryMonth(), cardEntity.getExpiryYear());
    }
}

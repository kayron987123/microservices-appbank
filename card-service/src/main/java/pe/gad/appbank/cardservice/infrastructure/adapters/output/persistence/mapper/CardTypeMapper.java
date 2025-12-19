package pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pe.gad.appbank.cardservice.domain.model.CardType;
import pe.gad.appbank.cardservice.domain.model.CardTypeId;
import pe.gad.appbank.cardservice.domain.model.Money;
import pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.entities.CardTypeEntity;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.WARN)
public interface CardTypeMapper {

    @Mapping(target = "id", source = "id.value")
    @Mapping(target = "annualFee", source = "annualFee.amount")
    CardTypeEntity toEntity(CardType cardType);

    @Mapping(target = "id", source = "id", qualifiedByName = "mapToCardTypeId")
    @Mapping(target = "network", source = "cardNetwork")
    @Mapping(target = "category", source = "cardCategory")
    @Mapping(target = "annualFee", source = "cardEntity", qualifiedByName = "mapToMoney")
    CardType toDomain(CardTypeEntity cardEntity);

    @Named("mapToCardTypeId")
    default CardTypeId mapToCardTypeId(UUID id) {
        return id != null ? CardTypeId.of(id) : null;
    }

    @Named("mapToMoney")
    default Money mapToMoney(CardTypeEntity cardTypeEntity) {
        return Money.of(cardTypeEntity.getAnnualFee());
    }
}

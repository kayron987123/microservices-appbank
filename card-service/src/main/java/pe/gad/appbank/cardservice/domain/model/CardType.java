package pe.gad.appbank.cardservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import pe.gad.appbank.cardservice.domain.enums.CardCategory;
import pe.gad.appbank.cardservice.domain.enums.CardNetwork;

@Getter
@Builder
public class CardType {
    private final CardTypeId id;
    private final String name;
    private final CardNetwork network;
    private final CardCategory category;
    private final Money annualFee;
    private final String description;
    private final boolean isActive;
}

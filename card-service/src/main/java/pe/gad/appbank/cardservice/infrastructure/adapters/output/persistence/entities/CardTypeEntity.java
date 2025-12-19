package pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pe.gad.appbank.cardservice.domain.enums.CardCategory;
import pe.gad.appbank.cardservice.domain.enums.CardNetwork;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_types")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardTypeEntity {
    @Id
    private UUID id;

    @Column(unique = true)
    private String name;

    @Column(name = "card_network", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardNetwork cardNetwork;

    @Column(name = "card_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardCategory cardCategory;

    @Column(name = "annual_fee")
    private BigDecimal annualFee;

    private String description;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}

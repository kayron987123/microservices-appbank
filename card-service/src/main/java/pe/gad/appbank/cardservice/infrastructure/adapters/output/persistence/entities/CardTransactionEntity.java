package pe.gad.appbank.cardservice.infrastructure.adapters.output.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import pe.gad.appbank.cardservice.domain.enums.CardStatus;
import pe.gad.appbank.cardservice.domain.enums.CardTransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardTransactionEntity {
    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;

    @Column(name = "account_id", nullable = false)
    private UUID accountId;

    private BigDecimal amount;

    @Column(name = "merchant_name", nullable = false)
    private String merchantName;

    @Column(name = "merchant_country", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardTransactionType transactionType;

    private CardStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;
}

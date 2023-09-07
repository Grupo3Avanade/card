package com.avanade.card.entities;

import com.avanade.card.enums.CardType;
import com.avanade.card.payloads.response.CardResponse;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.IdentifiableType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, unique = true, length = 16)
    private String number;

    @Column(nullable = false)
    private String holderName;

    @Column(nullable = false)
    private LocalDate expiry;

    @Column(nullable = false, length = 4)
    private String securityCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CardType type;

    @Column(nullable = false)
    private String bankAccountId;

    @Column(nullable = false)
    private Integer closingDay;

    private Boolean isDependent = Boolean.FALSE;

    //TODO mudar \/
    private UUID user;

    public CardResponse toResponse() {
        return new CardResponse(
                this.id,
                this.number,
                this.holderName,
                this.expiry,
                this.securityCode,
                this.type,
                this.bankAccountId,
                this.isDependent,
                this.closingDay,
                this.user
        );
    }

}

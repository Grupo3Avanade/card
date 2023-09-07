package com.avanade.card.entities;

import com.avanade.card.enums.CardType;
import jakarta.persistence.*;
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

}

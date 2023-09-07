package com.avanade.card.payloads.response;

import com.avanade.card.enums.CardType;

import java.time.LocalDate;
import java.util.UUID;

public record CardResponse(UUID id,
                           String number,
                           String holderName,
                           LocalDate expiry,
                           String securityCode,
                           CardType type,
                           String bankAccountId,
                           Boolean isDependent,
                           Integer closingDay,
                           UUID userId) {
}

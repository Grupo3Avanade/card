package com.avanade.card.payloads.request;

import java.time.LocalDate;
import java.util.UUID;

public record CardRequest(String holderName,
                          Boolean isDependent,

                          Integer closingDay,
                          UUID userId,

                          LocalDate expirationDate) {
}

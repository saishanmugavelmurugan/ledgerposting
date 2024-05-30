package com.zing.ledgerposting.model;

import com.zing.ledgerposting.enums.TransactionType;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record Transaction(
        @NotNull(message = "accountId not be null")String accountId,
        @NotNull(message = "type not be null")TransactionType type,
        @NotNull(message = "amount not be null")Double amount,
        @NotNull(message = "timestamp not be null")LocalDateTime timestamp) {
}

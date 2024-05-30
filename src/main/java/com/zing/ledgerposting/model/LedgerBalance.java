package com.zing.ledgerposting.model;

import com.zing.ledgerposting.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public record LedgerBalance(

        String accountId,
        TransactionType type,
        Double amount,
        LocalDateTime timestamp,
         Double balance,
        TransactionType balanceType
) {
}

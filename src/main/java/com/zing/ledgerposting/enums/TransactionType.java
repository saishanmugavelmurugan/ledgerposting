package com.zing.ledgerposting.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



public enum TransactionType {
    DR("dr"),CR("cr");
    final String  transactionType;
    TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    @JsonValue
    public String getTransactionType() {
        return transactionType;
    }

    @JsonCreator
    public static TransactionType fromValue(String value) {
        for (TransactionType type : values()) {
            String currentType = type.getTransactionType();
            if (currentType.equals(value)) {
                return type;
            }
        }

        // Return a response entity with a 400 Bad Request status
        throw new IllegalArgumentException("Invalid value for TransactionType Enum: " + value);
    }
}
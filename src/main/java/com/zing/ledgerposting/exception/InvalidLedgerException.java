package com.zing.ledgerposting.exception;

public class InvalidLedgerException extends RuntimeException{
    public InvalidLedgerException(final String s) {
        super(s);
    }

    InvalidLedgerException(final Throwable cause) {
        super(cause);
    }

}

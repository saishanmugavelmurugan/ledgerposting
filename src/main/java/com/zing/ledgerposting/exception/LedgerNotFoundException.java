package com.zing.ledgerposting.exception;

public class LedgerNotFoundException extends RuntimeException{
    public LedgerNotFoundException(final String s) {
        super(s);
    }

    LedgerNotFoundException(final Throwable cause) {
        super(cause);
    }

}

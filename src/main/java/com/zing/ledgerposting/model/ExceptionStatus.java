package com.zing.ledgerposting.model;

import org.springframework.http.HttpStatus;

public record ExceptionStatus(HttpStatus httpStatus, String message) {
}

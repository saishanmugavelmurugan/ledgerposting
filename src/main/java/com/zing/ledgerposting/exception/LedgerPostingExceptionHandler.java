package com.zing.ledgerposting.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.zing.ledgerposting.model.ExceptionStatus;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class LedgerPostingExceptionHandler {

    Logger logger = LoggerFactory.getLogger(LedgerPostingExceptionHandler.class);

    @ExceptionHandler(value = { InvalidLedgerException.class, DateTimeParseException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<Object> handleValidationException(Exception exception) {
        logger.error(exception.toString());
        String errorDetails = "Invalid data exception.";

        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ifx = (InvalidFormatException) exception.getCause();
            if (ifx.getTargetType()!=null && ifx.getTargetType().isEnum()) {
                errorDetails = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
                        ifx.getValue(), ifx.getPath().get(ifx.getPath().size()-1).getFieldName(), Arrays.toString(ifx.getTargetType().getEnumConstants()));
            }
        }
        ExceptionStatus errorResponse = new ExceptionStatus(HttpStatus.BAD_REQUEST,errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {LedgerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ResponseEntity<Object> handleNotFound(Exception ex) {
        return new ResponseEntity<>(new ExceptionStatus(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = { Exception.class})
    protected ResponseEntity<Object> handleExceptionRequest(Exception exception) {
        logger.error(exception.toString());
         return new ResponseEntity<>(new ExceptionStatus(HttpStatus.INTERNAL_SERVER_ERROR,exception.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

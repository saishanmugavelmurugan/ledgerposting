package com.zing.ledgerposting.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.springframework.http.HttpStatus.*;

@ExtendWith(SpringExtension.class)
public class LedgerPostingExceptionHandlerTest {
    @InjectMocks
    private LedgerPostingExceptionHandler ledgerPostingExceptionHandler;


    @Test
    public void whenLedgerBalanceNotFoundExceptionIsThrown_thenShouldReturnResponseStatus_NotFoundError() {
        //Given
        LedgerNotFoundException ledgerNotFoundException = new LedgerNotFoundException("not found");

        //When
        ResponseEntity<Object> responseError = ledgerPostingExceptionHandler.handleNotFound(ledgerNotFoundException);

        //Then
        assertThat(responseError.getStatusCode(), is(equalTo(NOT_FOUND)));

    }

    @Test
    public void whenInvalidLedgerExceptionIsThrown_thenShouldReturnResponseStatus_InvalidError() {
        //Given
        InvalidLedgerException invalidLedgerException = new InvalidLedgerException("invalid data");

        //When
        ResponseEntity<Object> responseError = ledgerPostingExceptionHandler.handleValidationException(invalidLedgerException);

        //Then
        assertThat(responseError.getStatusCode(), is(equalTo(BAD_REQUEST)));

    }
    @Test
    public void whenFeedbackAlreadyExistExceptionIsThrown_thenShouldReturnResponseStatus_AlreadyExistError() {
        //Given

        //When
        ResponseEntity<Object> responseError = ledgerPostingExceptionHandler.handleExceptionRequest(new Exception("Any General exception."));

        //Then
        assertThat(responseError.getStatusCode(), is(equalTo(INTERNAL_SERVER_ERROR)));

    }

}

package io.github.bank.exchange.handler;

import io.github.bank.common.dto.BankError;
import io.github.bank.exchange.exception.ExchangeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class ExchangeExceptionHandler {

    @ExceptionHandler(ExchangeException.class)
    public ResponseEntity<BankError> handleExchangeRateApiException(ExchangeException e) {
        return ResponseEntity
                .status(e.getResponseCode())
                .body(e.getErrorResponse());
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<BankError> handleException(Throwable e) {
        var error = BankError.builder()
                .code(INTERNAL_SERVER_ERROR.name())
                .message(e.getMessage())
                .details("Something went wrong!")
                .build();

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR.value())
                .body(error);
    }
}

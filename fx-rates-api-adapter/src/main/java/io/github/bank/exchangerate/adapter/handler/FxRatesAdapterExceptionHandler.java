package io.github.bank.exchangerate.adapter.handler;

import io.github.bank.common.dto.BankError;
import io.github.bank.exchangerate.adapter.exception.FxRatesApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static java.util.Objects.nonNull;
import static org.apache.logging.log4j.util.Strings.EMPTY;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class FxRatesAdapterExceptionHandler {

    @ExceptionHandler(FxRatesApiException.class)
    public ResponseEntity<BankError> handleExchangeRateApiException(FxRatesApiException e) {
        var error = BankError.builder()
                .code(INTERNAL_SERVER_ERROR.name())
                .message(nonNull(e.getErrorResponse()) ? e.getErrorResponse().getError() : EMPTY)
                .details(nonNull(e.getErrorResponse()) ? e.getErrorResponse().getDescription() : EMPTY)
                .build();

        return ResponseEntity
                .status(e.getResponseCode())
                .body(error);
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

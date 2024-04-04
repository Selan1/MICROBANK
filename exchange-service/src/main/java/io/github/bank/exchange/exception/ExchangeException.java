package io.github.bank.exchange.exception;

import io.github.bank.common.dto.BankError;
import lombok.Getter;

@Getter
public class ExchangeException extends RuntimeException {

    private final int responseCode;
    private final BankError errorResponse;

    public ExchangeException(int responseCode, BankError errorResponse) {
        this.responseCode = responseCode;
        this.errorResponse = errorResponse;
    }
}

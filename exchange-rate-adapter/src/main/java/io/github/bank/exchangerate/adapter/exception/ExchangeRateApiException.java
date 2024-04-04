package io.github.bank.exchangerate.adapter.exception;

import io.github.bank.exchangerate.adapter.dto.ExchangeRateApiErrorResponse;
import lombok.Data;
import lombok.Getter;

@Getter
public class ExchangeRateApiException extends RuntimeException {

    private final int responseCode;
    private final ExchangeRateApiErrorResponse errorResponse;

    public ExchangeRateApiException(int responseCode, ExchangeRateApiErrorResponse errorResponse) {
        this.responseCode = responseCode;
        this.errorResponse = errorResponse;
    }
}

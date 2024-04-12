package io.github.bank.exchangerate.adapter.exception;

import io.github.bank.exchangerate.adapter.dto.FxRatesApiErrorResponse;
import lombok.Getter;

@Getter
public class FxRatesApiException extends RuntimeException {

    private final int responseCode;
    private final FxRatesApiErrorResponse errorResponse;

    public FxRatesApiException(int responseCode, FxRatesApiErrorResponse errorResponse) {
        this.responseCode = responseCode;
        this.errorResponse = errorResponse;
    }
}

package io.github.bank.exchangerate.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRateApiErrorResponse {

    private String result;
    private String documentation;

    @JsonProperty("terms_of_use")
    private String termsOfUse;

    @JsonProperty("error-type")
    private String errorType;
}
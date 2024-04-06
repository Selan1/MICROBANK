package io.github.bank.exchangerate.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ExchangeRateApiResponse {

    private String success;

    @JsonProperty("terms")
    private String termsOfUse;

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("time_next_update_unix")
    private Long nextUpdateUnix;

    private Integer year;

    private Integer month;

    private Integer day;

    @JsonProperty("date")
    private String date;

    @JsonProperty("base")
    private String baseCode;

    @JsonProperty("target_code")
    private String targetCode;

    @JsonProperty("rates")
    private Double conversationRate;

    @JsonProperty("conversion_result")
    private Double conversationResult;

    @JsonProperty("conversion_rates")
    private Map<String, Double> rates;
}
package io.github.bank.exchangerate.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ExchangeRateApiResponse {

    private String result;
    private String documentation;

    @JsonProperty("terms_of_use")
    private String termsOfUse;

    @JsonProperty("time_last_update_unix")
    private Long lastUpdatedUnix;

    @JsonProperty("time_next_update_unix")
    private Long nextUpdateUnix;

    private Integer year;

    private Integer month;

    private Integer day;

    @JsonProperty("base_code")
    private String baseCode;

    @JsonProperty("target_code")
    private String targetCode;

    @JsonProperty("conversion_rate")
    private Double conversationRate;

    @JsonProperty("conversion_result")
    private Double conversationResult;

    @JsonProperty("conversion_rates")
    private Map<String, Double> rates;
}
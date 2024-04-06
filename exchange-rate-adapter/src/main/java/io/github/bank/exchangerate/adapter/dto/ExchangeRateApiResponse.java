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

    @JsonProperty("from")
    private String targetFrom;

    @JsonProperty("to")
    private String targetTo;

    @JsonProperty("info")
    private Double info;

    @JsonProperty("rate")
    private Double rate;

    @JsonProperty("rates")
    private Double conversationRate;

    @JsonProperty("result")
    private Double conversationResult;

    @JsonProperty("conversion_rates")
    private Map<String, Double> rates;
}
package io.github.bank.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRates {
    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    private LocalDate rateDate;
    private String baseCurrency;
    private Map<String, Double> exchangeRates;
}
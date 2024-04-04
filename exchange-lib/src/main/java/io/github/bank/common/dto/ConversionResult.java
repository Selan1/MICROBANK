package io.github.bank.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResult {

    private LocalDateTime operationDate;
    private String fromCurrency;
    private String toCurrency;
    private Double initialAmount;
    private Double conversionRate;
    private Double totalResult;
}
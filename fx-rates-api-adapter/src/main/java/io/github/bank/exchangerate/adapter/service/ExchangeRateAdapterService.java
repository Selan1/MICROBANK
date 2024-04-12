package io.github.bank.exchangerate.adapter.service;

import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchangerate.adapter.client.FxRatesApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ExchangeRateAdapterService {

    private final FxRatesApiClient client;

    @Value("${adapter.client.fx-rates.api-key}")
    private String apiKey;

    public ExchangeRates getActualRates(String baseCurrency) {
        var actualRates = client.getActualRates(apiKey, baseCurrency);

        return ExchangeRates.builder()
                .baseCurrency(actualRates.getBase())
                .rateDate(actualRates.getDate().toLocalDate())
                .exchangeRates(actualRates.getRates())
                .build();
    }

    public List<ExchangeRates> getHistoricalRates(String baseCurrency, LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.isAfter(dateTo)) return List.of();

        var exchangeRates = new ArrayList<ExchangeRates>();
        while (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo)) {

            var historyDailyRate = client.getHistoricalRates(apiKey, baseCurrency, dateFrom);

            exchangeRates.add(ExchangeRates.builder()
                    .baseCurrency(historyDailyRate.getBase())
                    .rateDate(historyDailyRate.getDate().toLocalDate())
                    .exchangeRates(historyDailyRate.getRates())
                    .build());

            dateFrom = dateFrom.plusDays(1);
        }

        return exchangeRates;
    }

    public ConversionResult convertRates(String fromCurrency, String toCurrency, Double amount) {

        var conversionResult = client.convert(apiKey, fromCurrency, toCurrency, amount);

        return ConversionResult.builder()
                .fromCurrency(conversionResult.getQuery().getFrom())
                .toCurrency(conversionResult.getQuery().getTo())
                .conversionRate(conversionResult.getInfo().getRate())
                .operationDate(LocalDateTime.now())
                .initialAmount(conversionResult.getQuery().getAmount())
                .totalResult(conversionResult.getResult())
                .build();
    }
}

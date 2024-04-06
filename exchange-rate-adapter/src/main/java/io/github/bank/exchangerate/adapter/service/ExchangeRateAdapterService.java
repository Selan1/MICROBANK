package io.github.bank.exchangerate.adapter.service;

import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchangerate.adapter.client.ExchangeRateApiClient;
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

    private final ExchangeRateApiClient client;

    @Value("${adapter.client.exchange-rate.api-key}")
    private String apiKey;

    public ExchangeRates getActualRates(String baseCurrency) {
        var actualRates = client.getActualRates(apiKey, baseCurrency);

        var actualizationDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(actualRates.getLastUpdatedUnix()), ZoneId.of("UTC"));

        return ExchangeRates.builder()
                .baseCurrency(actualRates.getBaseCode())
                .rateDate(actualizationDateTime.toLocalDate())
                .exchangeRates(actualRates.getRates())
                .build();
    }

    public List<ExchangeRates> getHistoricalRates(String baseCurrency, LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom.isAfter(dateTo)) return List.of();

        var exchangeRates = new ArrayList<ExchangeRates>();
        while (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo)) {

            var historyDailyRate = client.getHistoricalRates(apiKey, baseCurrency,
                    String.valueOf(dateFrom.getYear()),
                    String.valueOf(dateFrom.getMonthValue()),
                    String.valueOf(dateFrom.getDayOfMonth()));

            var actualizationDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(historyDailyRate.getLastUpdatedUnix()), ZoneId.of("UTC"));

            exchangeRates.add(ExchangeRates.builder()
                    .baseCurrency(historyDailyRate.getBaseCode())
                    .rateDate(actualizationDateTime.toLocalDate())
                    .exchangeRates(historyDailyRate.getRates())
                    .build());

            dateFrom = dateFrom.plusDays(1);
        }

        return exchangeRates;
    }

    public ConversionResult convertRates(String fromCurrency, String toCurrency, Double amount) {
        var conversionResult = client.convert(apiKey, fromCurrency, toCurrency, amount);

        return ConversionResult.builder()
                .fromCurrency(conversionResult.getBaseCode())
                .toCurrency(conversionResult.getTargetCode())
                .conversionRate(conversionResult.getConversationRate())
                .operationDate(LocalDateTime.now())
                .initialAmount(amount)
                .totalResult(conversionResult.getConversationResult())
                .build();
    }
}

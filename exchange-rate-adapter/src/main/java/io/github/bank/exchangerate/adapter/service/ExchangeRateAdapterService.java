package io.github.bank.exchangerate.adapter.service;

import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchangerate.adapter.client.ExchangeRateApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        var actualRates = client.getActualRates(baseCurrency);


        return ExchangeRates.builder()
                .baseCurrency(actualRates.getBaseCode())
                .rateDate(actualRates.getDate())
                .exchangeRates(actualRates.getRates())
                .build();
    }

    public List<ExchangeRates> getHistoricalRates(String baseCurrency, String dateFrom, String dateTo) {
        LocalDate dateF = LocalDate.parse(dateFrom);;
        LocalDate dateT = LocalDate.parse(dateTo);;

        if (dateF.isAfter(dateT)) return List.of();

        var exchangeRates = new ArrayList<ExchangeRates>();
        while (dateF.isBefore(dateT) || dateF.isEqual(dateT)) {

            var historyDailyRate = client.getHistoricalRates(
                    apiKey,
                    baseCurrency,
                    String.valueOf(dateF.getYear()),
                    String.valueOf(dateF.getMonthValue()),
                    String.valueOf(dateF.getDayOfMonth()));



            exchangeRates.add(ExchangeRates.builder()
                    .baseCurrency(historyDailyRate.getBaseCode())
                    .rateDate(historyDailyRate.getTimestamp())
                    .exchangeRates(historyDailyRate.getRates())
                    .build());

            dateF = dateF.plusDays(1);
        }

        return exchangeRates;
    }

    public ConversionResult convertRates(String fromCurrency, String toCurrency, Double amount) {
        var conversionResult = client.convert(apiKey, fromCurrency, toCurrency, amount);

        return ConversionResult.builder()
                .fromCurrency(conversionResult.getTargetFrom())
                .toCurrency(conversionResult.getTargetTo())
                .conversionRate(conversionResult.getInfo())
                .operationDate(LocalDateTime.now())
                .initialAmount(amount)
                .totalResult(conversionResult.getConversationResult())
                .build();
    }
}

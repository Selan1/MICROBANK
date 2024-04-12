package io.github.bank.exchange.mapping;

import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchange.entity.HistoryRate;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@UtilityClass
public class ExchangeRatesMapper {

    public static ExchangeRates fromUsdRateHistory(List<HistoryRate> historyRate, String currency, LocalDate date) {
        var usdRates = historyRate.stream()
                .collect(toMap(rate -> rate.getCurrency(), rate -> rate.getUsdRate()));

        var currencyUsdRate = usdRates.get(currency);
        var multiplier = 1 / currencyUsdRate;

        var currencyRates = usdRates.entrySet().stream()
                .collect(toMap(entry -> entry.getKey(), rate -> multiplier * rate.getValue()));

        // usd -> rub
        // usd -> EUR
        // rub -> eur

        // (1 / rub) * eur


        return ExchangeRates.builder()
                .rateDate(date)
                .baseCurrency(currency)
                .exchangeRates(currencyRates)
                .build();
    }

    public static List<HistoryRate> toExchangeRateHistory(List<ExchangeRates> exchangeRates) {
        return exchangeRates.stream()
                .flatMap(dayRate -> dayRate.getExchangeRates().entrySet().stream()
                        .map(rate -> HistoryRate.builder()
                                .actualDate(dayRate.getRateDate())
                                .currency(rate.getKey())
                                .usdRate(rate.getValue())
                                .build()))
                .collect(toList());
    }
}

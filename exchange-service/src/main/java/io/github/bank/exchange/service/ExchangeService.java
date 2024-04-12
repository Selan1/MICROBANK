package io.github.bank.exchange.service;

import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchange.client.RateAdapterClient;
import io.github.bank.exchange.repository.RateHistoryDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static io.github.bank.exchange.mapping.ExchangeRatesMapper.fromUsdRateHistory;
import static io.github.bank.exchange.mapping.ExchangeRatesMapper.toExchangeRateHistory;


@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final RateAdapterClient adapterClient;
    private final RateHistoryDateRepository repository;


    public ExchangeRates getActualRates(String baseCurrency) {
        return adapterClient.getActualRates(baseCurrency);
    }

    public ExchangeRates getHistoricalRate(String baseCurrency, LocalDate date) {
        var historyRates = repository.findHistoryRateByActualDate(date);

        if (historyRates.isEmpty()) {
            var exchangeRates = adapterClient.getHistoricalRates("USD", date, date);
            historyRates = repository.saveAll(toExchangeRateHistory(exchangeRates));
        }

        return fromUsdRateHistory(historyRates, baseCurrency, date);
    }

    public ConversionResult convertRates(String fromCurrency, String toCurrency, Double amount) {
        return adapterClient.convert(fromCurrency, toCurrency, amount);
    }
}

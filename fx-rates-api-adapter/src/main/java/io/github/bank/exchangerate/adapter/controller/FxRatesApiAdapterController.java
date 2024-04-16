package io.github.bank.exchangerate.adapter.controller;

import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchangerate.adapter.service.ExchangeRateAdapterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@Log4j2
@RestController

@RequestMapping("/fx-rates-api")
@RequiredArgsConstructor
public class FxRatesApiAdapterController {

    private final ExchangeRateAdapterService rateAdapterService;

    @GetMapping("/actual")
    public ExchangeRates getActualExchangeRate(@RequestParam String currency) {
        log.info("getActualExchangeRate: " + currency);
        return rateAdapterService.getActualRates(currency);
    }

    @GetMapping("/history")
    public List<ExchangeRates> getHistoricalExchangeRate(@RequestParam String currency,
                                                         @RequestParam @DateTimeFormat(iso = DATE) LocalDate dateFrom,
                                                         @RequestParam @DateTimeFormat(iso = DATE) LocalDate dateTo) {
        log.info("getHistoricalExchangeRate: " + currency + ", " + dateFrom + ", " + dateTo);
        return rateAdapterService.getHistoricalRates(currency, dateFrom, dateTo);
    }

    @GetMapping("/convert")
    public ConversionResult convertRates(@RequestParam String fromCurrency,
                                         @RequestParam String toCurrency,
                                         @RequestParam Double amount) {
        log.info("convertRates: " + fromCurrency + ", " + toCurrency + ", " + amount);
        return rateAdapterService.convertRates(fromCurrency, toCurrency, amount);
    }
}

package io.github.bank.exchangerate.adapter.controller;

import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.common.dto.HistoryResult;
import io.github.bank.exchangerate.adapter.service.ExchangeRateAdapterService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController

@RequestMapping("/exchange-rate")
@RequiredArgsConstructor
public class ExchangeRateAdapterController {

    private final ExchangeRateAdapterService rateAdapterService;

    @GetMapping("/actual")
    public ExchangeRates getActualExchangeRate(@RequestParam String currency) {
        return rateAdapterService.getActualRates(currency);
    }

    @GetMapping("/history")
    public HistoryResult getHistoricalExchangeRate(@RequestParam String currency,
                                                   @RequestParam String dateFrom,
                                                   @RequestParam String dateTo) {
        return rateAdapterService.getHistoricalRates(currency, dateFrom, dateTo);
    }

    @GetMapping("/convert")
    public ConversionResult convertRates(@RequestParam String fromCurrency,
                                         @RequestParam String toCurrency,
                                         @RequestParam Double amount) {
        return rateAdapterService.convertRates(fromCurrency, toCurrency, amount);
    }
}

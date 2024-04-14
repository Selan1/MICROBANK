package io.github.bank.exchange.controller;

import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchange.service.ExchangeService;
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

@RequestMapping("/exchange")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService rateAdapterService;

    @GetMapping("/actual")
    public ExchangeRates getActualExchangeRate(@RequestParam String currency) {
        return rateAdapterService.getActualRates(currency);
    }

    @GetMapping("/history")
    public ExchangeRates getHistoricalExchangeRate(@RequestParam String currency,
                                                   @RequestParam @DateTimeFormat(iso = DATE) LocalDate date) {
        return rateAdapterService.getHistoricalRate(currency, date);
    }

    @GetMapping("/convert")
    public ConversionResult convertRates(@RequestParam String fromCurrency,
                                         @RequestParam String toCurrency,
                                         @RequestParam Double amount) {
        return rateAdapterService.convertRates(fromCurrency, toCurrency, amount);
    }
}

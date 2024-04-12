package io.github.bank.exchange.client;


import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@FeignClient(name = "rateAdapterClient", url = "${adapter.client.rate-adapter.url}")
public interface RateAdapterClient {

    @GetMapping(value = "/actual")
    ExchangeRates getActualRates(@RequestParam String currency);

    @GetMapping(value = "/history")
    List<ExchangeRates> getHistoricalRates(@RequestParam String currency,
                                           @RequestParam @DateTimeFormat(iso = DATE) LocalDate dateFrom,
                                           @RequestParam @DateTimeFormat(iso = DATE) LocalDate dateTo);

    @GetMapping(value = "/convert")
    ConversionResult convert(@RequestParam String fromCurrency,
                             @RequestParam String toCurrency,
                             @RequestParam Double amount);
}

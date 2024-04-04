package io.github.bank.exchange.client;


import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "exchangeApiClient", url = "${adapter.client.exchange-rate-adapter.url}")
public interface ExchangeRateAdapterClient {

    @GetMapping(value = "/exchange-rate/actual")
    ExchangeRates getActualRates(@RequestParam String currency);

    @GetMapping(value = "/exchange-rate/history")
    List<ExchangeRates> getHistoricalRates(@RequestParam String currency,
                                           @PathVariable LocalDate dateFrom,
                                           @PathVariable LocalDate dateTo);

    @GetMapping(value = "/exchange-rate/convert")
    ConversionResult convert(@RequestParam String fromCurrency,
                             @RequestParam String toCurrency,
                             @RequestParam Double amount);
}

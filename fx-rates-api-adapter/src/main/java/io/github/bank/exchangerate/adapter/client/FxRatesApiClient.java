package io.github.bank.exchangerate.adapter.client;


import io.github.bank.exchangerate.adapter.dto.FxRatesApiResponse;
import io.github.bank.exchangerate.adapter.dto.FxRatesConvertResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@FeignClient(name = "fxRatesApiClient", url = "${adapter.client.fx-rates.url}")
public interface FxRatesApiClient {

    @GetMapping(value = "/latest")
    FxRatesApiResponse getActualRates(@RequestParam String apiKey,
                                      @RequestParam String base);

    @GetMapping(value = "/historical")
    FxRatesApiResponse getHistoricalRates(@RequestParam String apiKey,
                                          @RequestParam String base,
                                          @RequestParam @DateTimeFormat(iso = DATE) LocalDate date);

    @GetMapping(value = "/convert")
    FxRatesConvertResponse convert(@RequestParam String apiKey,
                                   @RequestParam String from,
                                   @RequestParam String to,
                                   @RequestParam Double amount);
}

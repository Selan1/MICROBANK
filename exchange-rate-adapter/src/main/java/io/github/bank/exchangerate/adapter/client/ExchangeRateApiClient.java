package io.github.bank.exchangerate.adapter.client;


import io.github.bank.exchangerate.adapter.dto.ExchangeRateApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchangeApiClient", url = "${adapter.client.exchange-rate.url}")
public interface ExchangeRateApiClient {
    //https://api.fxratesapi.com/latest?base=USD&format=json
    @GetMapping(value = "latest?api_key=9d16deaa1b898fdc43d3e23b7d6&base={currency}&format=json")
    ExchangeRateApiResponse getActualRates(@PathVariable String currency);
//"https://api.fxratesapi.com/historical?api_key=fxr_live_caae3d32b9d16deaa1b898fdc43d3e23b7d6&date=2020-01-01&base=USD"
    @GetMapping(value = "historical?date={year}-{month}-{day}&base={currency}")
    ExchangeRateApiResponse getHistoricalRates(@PathVariable String apiKey,
                                               @PathVariable String currency,
                                               @PathVariable String year,
                                               @PathVariable String month,
                                               @PathVariable String day);

    @GetMapping(value = "convert?{apiKey}&from={fromCurrency}&to={fromCurrency}&amount={amount}}")
    ExchangeRateApiResponse convert(@PathVariable String apiKey,
                                    @PathVariable String fromCurrency,
                                    @PathVariable String toCurrency,
                                    @PathVariable Double amount);
}

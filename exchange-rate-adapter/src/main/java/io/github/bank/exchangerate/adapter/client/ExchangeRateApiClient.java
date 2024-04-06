package io.github.bank.exchangerate.adapter.client;


import io.github.bank.exchangerate.adapter.dto.ExchangeRateApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "exchangeApiClient", url = "${adapter.client.exchange-rate.url}")
public interface ExchangeRateApiClient {
    //https://api.fxratesapi.com/latest?base=USD&format=json
    @GetMapping(value = "latest?base={currency}&format=json")
    ExchangeRateApiResponse getActualRates(@PathVariable String currency);
//"https://api.fxratesapi.com/historical?api_key=fxr_demo_asdiksd21&date=2020-01-01&base=USD"
    @GetMapping(value = "historical?api_key={apiKey}&format=json&date={year}-{month}-{day}&base={currency}")
    ExchangeRateApiResponse getHistoricalRates(@PathVariable String apiKey,
                                               @PathVariable String currency,
                                               @PathVariable String year,
                                               @PathVariable String month,
                                               @PathVariable String day);

    @GetMapping(value = "/{apiKey}/pair/{fromCurrency}/{toCurrency}/{amount}")
    ExchangeRateApiResponse convert(@PathVariable String apiKey,
                                           @PathVariable String fromCurrency,
                                           @PathVariable String toCurrency,
                                           @PathVariable Double amount);
}

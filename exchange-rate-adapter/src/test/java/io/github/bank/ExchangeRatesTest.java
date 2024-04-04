package io.github.bank;

import io.github.bank.configuration.BankApplicationConfiguration;
import io.github.bank.model.rates.CurrencyNames;
import io.github.bank.model.rates.GetExchangeRateRequest;
import io.github.bank.model.rates.GetExchangeRateResponse;
import io.github.bank.service.ExchangeRateAdapterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableFeignClients(basePackages = {"io.github.bank.client.exchangerate"})
@EnableAutoConfiguration
@SpringBootTest(classes = {BankApplicationConfiguration.class, ExchangeRateAdapterService.class})
public class ExchangeRatesTest {

    @Autowired
    private ExchangeRateAdapterService bankService;

    @Test
    public void testRUBRates() {
        GetExchangeRateRequest serviceRequest = GetExchangeRateRequest.builder()
                .currencies(CurrencyNames.builder()
                        .currencyName("RUB")
                        .build())
                .build();

        GetExchangeRateResponse serviceResponse = bankService.getExchangeRate(serviceRequest);
        System.out.println(serviceResponse);
    }
}


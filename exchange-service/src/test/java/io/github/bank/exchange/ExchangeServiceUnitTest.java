package io.github.bank.exchange;

import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchange.client.RateAdapterClient;
import io.github.bank.exchange.entity.HistoryRate;
import io.github.bank.exchange.repository.RateHistoryDateRepository;
import io.github.bank.exchange.service.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceUnitTest {

    @Mock
    private RateAdapterClient rateAdapterClient;

    @Mock
    private RateHistoryDateRepository repository;

    @InjectMocks
    private ExchangeService exchangeService;

    @BeforeEach
    void initMocks() {
        when(repository.findHistoryRateByActualDate(any())).thenReturn(List.of());

        when(rateAdapterClient.getHistoricalRates(anyString(), any(), any()))
                .thenReturn(List.of(ExchangeRates.builder()
                        .baseCurrency("USD")
                        .rateDate(LocalDate.now())
                        .exchangeRates(Map.of("USD", 1.0, "RUB", 92.00, "EUR", 0.95))
                        .build()));

        when(repository.saveAll(any())).thenReturn(
                List.of(HistoryRate.builder()
                                .id(1L)
                                .currency("USD")
                                .usdRate(1.0)
                                .actualDate(LocalDate.now())
                                .build(),
                        HistoryRate.builder()
                                .id(1L)
                                .currency("RUB")
                                .usdRate(92.00)
                                .actualDate(LocalDate.now())
                                .build(),
                        HistoryRate.builder()
                                .id(2L)
                                .currency("EUR")
                                .usdRate(0.95)
                                .actualDate(LocalDate.now())
                                .build()));
    }

    @Test
    void getHistoricalRate() {

        var historicalResult = exchangeService.getHistoricalRate("EUR", LocalDate.now());

        assertEquals(LocalDate.now(), historicalResult.getRateDate());
        assertEquals("EUR", historicalResult.getBaseCurrency());
        assertEquals(3, historicalResult.getExchangeRates().entrySet().size());
        assertEquals(1.0, historicalResult.getExchangeRates().get("EUR"));
        assertEquals(1.05, historicalResult.getExchangeRates().get("USD"));
        assertEquals(96.84, historicalResult.getExchangeRates().get("RUB"));
    }
}


package io.github.bank;

import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchangerate.adapter.client.FxRatesApiClient;
import io.github.bank.exchangerate.adapter.dto.FxRatesApiResponse;
import io.github.bank.exchangerate.adapter.service.ExchangeRateAdapterService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ExchangeRatesTest {
    @Mock
    private FxRatesApiClient fxRatesApiClient;

    @InjectMocks
    private ExchangeRateAdapterService exchangeRateAdapterService;

    @Test
    void testApiHistoricalRates() {


        when(fxRatesApiClient.getHistoricalRates(202, any(), any()))
                .thenReturn(FxRatesApiResponse.builder()
                        .date(LocalDateTime.now())
                        .base("USD")
                        .rates(Map.of("EUR", 0.85))
                        .build());
        when(fxRatesApiClient.getHistoricalRates(any(), any(), any()))
                .thenReturn(FxRatesApiResponse.builder()
                        .date(LocalDateTime.now())
                        .base("USD")
                        .rates(Map.of("EUR", 0.85))
                        .build());
        when(fxRatesApiClient.getHistoricalRates(any(), any(), any()))
                .thenReturn(FxRatesApiResponse.builder()
                        .date(LocalDateTime.now())
                        .base("USD")
                        .rates(Map.of("EUR", 0.85))
                        .build());



        LocalDate dateFrom = LocalDate.of(2022, 3, 1);
        LocalDate dateTo = LocalDate.of(2022, 3, 3);

        List<ExchangeRates> result = exchangeRateAdapterService.getHistoricalRates("EUR", dateFrom, dateTo);

        // Проверяем, что возвращенный список не пустой и содержит нужное количество элементов
        assertFalse(result.isEmpty());
        assertEquals(3, result.size());

        // Проверяем, что даты у объектов ExchangeRates соответствуют ожидаемым
        for (ExchangeRates exchangeRates : result) {
            assertTrue(exchangeRates.getRateDate().isEqual(dateFrom));
            dateFrom = dateFrom.plusDays(1);
        }
    }
}




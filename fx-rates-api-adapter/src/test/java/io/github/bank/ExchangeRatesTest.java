package io.github.bank;


import static org.junit.jupiter.api.Assertions.*;

import io.github.bank.exchangerate.adapter.dto.FxRatesApiResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import java.time.LocalDate;

import io.github.bank.exchangerate.adapter.client.FxRatesApiClient;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchangerate.adapter.service.ExchangeRateAdapterService;


@ExtendWith(MockitoExtension.class)
class ExchangeRatesTest {
    @Mock
    private FxRatesApiClient fxRatesApiClient;

    @InjectMocks
    private ExchangeRateAdapterService exchangeRateAdapterService;

    @Test
    void testApiHistoricalRates() {


        when(fxRatesApiClient.getHistoricalRates(any(), any(), eq(LocalDate.of(2022,3,1))))
                .thenReturn(FxRatesApiResponse.builder()
                        .date(LocalDateTime.of(2022,3,1,0,0))
                        .base("USD")
                        .rates(Map.of("EUR", 0.85))
                        .build());


        when(fxRatesApiClient.getHistoricalRates(any(), any(), eq(LocalDate.of(2022,3,2))))
                .thenReturn(FxRatesApiResponse.builder()
                        .date(LocalDateTime.of(2022,3,2,0,0))
                        .base("USD")
                        .rates(Map.of("EUR", 0.95))
                        .build());


        when(fxRatesApiClient.getHistoricalRates(any(), any(), eq(LocalDate.of(2022,3,3))))
                .thenReturn(FxRatesApiResponse.builder()
                        .date(LocalDateTime.of(2022,3,3,0,0))
                        .base("USD")
                        .rates(Map.of("EUR", 0.75))
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




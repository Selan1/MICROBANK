package io.github.bank.exchange;

import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchange.client.RateAdapterClient;
import io.github.bank.exchange.repository.RateHistoryDateRepository;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith({MockitoExtension.class})
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@AutoConfigureEmbeddedDatabase
public class ExchangeServiceIntegrationTest {

    @MockBean
    private RateAdapterClient rateAdapterClient;

    @Autowired
    private RateHistoryDateRepository repository;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void initMocks() {
        when(rateAdapterClient.getHistoricalRates(anyString(), any(), any()))
                .thenReturn(List.of(ExchangeRates.builder()
                        .baseCurrency("USD")
                        .rateDate(LocalDate.now())
                        .exchangeRates(Map.of("USD", 1.0, "RUB", 92.00, "EUR", 0.95))
                        .build()));
    }

    @Test
    @SneakyThrows
    void getHistoricalRate() {

        mvc.perform(get("/exchange/history")
                        .param("currency", "EUR")
                        .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rateDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.baseCurrency").value("EUR"))
                .andExpect(jsonPath("$.exchangeRates.EUR").value(1.0))
                .andExpect(jsonPath("$.exchangeRates.USD").value(1.05))
                .andExpect(jsonPath("$.exchangeRates.RUB").value(96.84));

        var savedHistory = repository.findAll();
        assertEquals(3, savedHistory.size());
    }
}

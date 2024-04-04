package io.github.bank.exchange.service;

import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchange.client.ExchangeRateAdapterClient;
import io.github.bank.exchange.mapping.ExchangeRatesMapper;
import io.github.bank.exchange.repository.RateHistoryDateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.bank.exchange.mapping.ExchangeRatesMapper.fromExchangeRateHistory;
import static java.util.stream.Collectors.toList;

// Аннотация @Service указывает, что класс ExchangeService
// является сервисным компонентом и может быть инъектирован в другие компоненты.
@Service
// Аннотация @RequiredArgsConstructor перед классом ExchangeService говорит о том,
// что Lombok должен создать конструктор для всех полей класса, помеченных как final или @NonNull.
@RequiredArgsConstructor
// Объявление класса ExchangeService, который, как уже упоминалось, предоставляет функционал для работы с данными
// об обменных курсах.
public class ExchangeService {
    //Объявление приватного final поля exchangeRateAdapterClient типа ExchangeRateAdapterClient,
    // который предоставляет клиент для взаимодействия с внешним сервисом обмена.
    private final ExchangeRateAdapterClient adapterClient;
    // Объявление приватного final поля rateHistoryDateRepository типа RateHistoryDateRepository,
    // который предоставляет доступ к историческим данным об обменных курсах.
    private final RateHistoryDateRepository repository;

//  Метод возвращает список курса определенной валюты
    public ExchangeRates getActualRates(String baseCurrency) {
        // Возвращает актуальные обменные курсы для указанной базовой валюты, используя клиент adapterClient.
        return adapterClient.getActualRates(baseCurrency);
    }


    //метод возвращает список объектов типа ExchangeRates  используется для получения исторических обменных курсов
    // для указанной базовой валюты и заданного периода времени
    public List<ExchangeRates> getHistoricalRates(String baseCurrency, LocalDate dateFrom, LocalDate dateTo) {
        //Получает исторические обменные курсы из репозитория repository за определенный период времени
        // с dateFrom по dateTo
        var historyRates = repository.findRateHistoryByDates(dateFrom, dateTo);

        if (!historyRates.isEmpty()) {
            return historyRates.stream()
                    //Преобразует объекты типа ExchangeRates в объекты типа ExchangeRateHistory с помощью метода
                    // fromExchangeRateHistory класса ExchangeRatesMapper.
                    .map(ExchangeRatesMapper::fromExchangeRateHistory)
                    //  .collect(toList()) в данном контексте завершает операцию потока (stream) после того,
                    //  как было применено отображение объектов типа ExchangeRates на объекты типа
                    //  ExchangeRateHistory с помощью метода fromExchangeRateHistory из класса ExchangeRatesMapper.
                    .collect(toList());
        }
// В этой строке создается переменная exchangeRates, в которую сохраняются исторические обменные курсы,
// полученные из клиента adapterClient для указанной базовой валюты baseCurrency и периода времени с dateFrom до dateTo.
        var exchangeRates = adapterClient.getHistoricalRates(baseCurrency, dateFrom, dateTo);

        //Эта строка проверяет, является ли базовая валюта равной "USD".
        if ("USD".equals(baseCurrency)) {
            //Внутри ветки условия создается переменная historyRates, в которую сохраняются объекты ExchangeRateHistory.
            // Для этого из списка exchangeRates создается поток (stream),
            // к каждому элементу этого списка применяется метод преобразования
            // ExchangeRatesMapper::toExchangeRateHistory, после чего результат собирается обратно в список
            // с помощью метода collect(toList()).
            historyRates = exchangeRates.stream()
                    .map(ExchangeRatesMapper::toExchangeRateHistory)
                    .collect(toList());
            // Здесь вызывается метод saveAll объекта repository, который сохраняет
            // все объекты ExchangeRateHistory, содержащиеся в списке historyRates.
            repository.saveAll(historyRates);
        }
        //
        return exchangeRates;
    }
// Объявляется публичный метод convertRates, который принимает три параметра: fromCurrency (исходная валюта),
// toCurrency (валюта конвертации) и amount (сумма для конвертации).
    public ConversionResult convertRates(String fromCurrency, String toCurrency, Double amount) {
        // Возвращает результат конвертации заданной суммы amount из исходной валюты fromCurrency в валюту конвертации
        // toCurrency, используя метод convert объекта adapterClient.
        return adapterClient.convert(fromCurrency, toCurrency, amount);
    }
}

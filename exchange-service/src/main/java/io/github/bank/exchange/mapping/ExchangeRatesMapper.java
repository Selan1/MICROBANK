package io.github.bank.exchange.mapping;

import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchange.entity.RateHistoryDate;
import io.github.bank.exchange.entity.RateHistoryValue;
import lombok.experimental.UtilityClass;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
// Аннотация Lombok @UtilityClass применяется к классу и позволяет создать утилитарный класс
// с приватным конструктором и статическими методами. В данном случае класс ExchangeRatesMapper
// будет содержать только статические методы без возможности создания экземпляра этого класса.
@UtilityClass
public class ExchangeRatesMapper {
    // Это объявление публичного статического метода fromExchangeRateHistory, который принимает объект типа
    // RateHistoryDate как параметр и возвращает объект типа ExchangeRates.
    // Данный метод будет преобразовывать исторические данные об обменных курсах из объекта
    // RateHistoryDate в объект ExchangeRates.
    public static ExchangeRates fromExchangeRateHistory(RateHistoryDate rateHistoryDate) {
        // Создание нового объекта ExchangeRates с помощью паттерна Builder,
        // который позволяет инициализировать объект с различными полями.
        return ExchangeRates.builder()
                //Установка значения для поля rateDate объекта ExchangeRates с использованием
                // актуальной даты из объекта RateHistoryDate.
                .rateDate(rateHistoryDate.getActualDate())
                //Установка значения для поля baseCurrency объекта ExchangeRates.
                // В данном случае устанавливается базовая валюта как "USD".
                .baseCurrency("USD")
                //Установка значения для поля exchangeRates объекта ExchangeRates.
                // Здесь происходит использование потоков данных (Stream) для преобразования списка
                // RateHistoryValue из rateValues объекта RateHistoryDate в Map, где ключом будет валюта,
                // а значением - обменный курс.
                .exchangeRates(rateHistoryDate.getRateValues().stream()
                        //Использование метода collect для преобразования элементов потока в Map, где ключ -
                        // это валюта (currency), а значение - обменный курс (rate) из объектов RateHistoryValue.
                        .collect(toMap(RateHistoryValue::getCurrency, RateHistoryValue::getRate)))
                // Завершение построения объекта ExchangeRates с установленными значениями для его полей и возврат
                // этого объекта из метода fromExchangeRateHistory.
                .build();
    }

    public static RateHistoryDate toExchangeRateHistory(ExchangeRates exchangeRates) {
        var historyRateDate = RateHistoryDate.builder()
                .actualDate(exchangeRates.getRateDate())
                .build();

        var rates = exchangeRates.getExchangeRates().entrySet().stream()
                .map(entry -> RateHistoryValue.builder()
                        .currency(entry.getKey())
                        .rate(entry.getValue())
                        .rateHistoryDate(historyRateDate)
                        .build())
                .collect(toList());

        historyRateDate.setRateValues(rates);

        return historyRateDate;
    }
}

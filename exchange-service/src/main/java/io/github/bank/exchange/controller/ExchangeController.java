package io.github.bank.exchange.controller;

import io.github.bank.common.dto.ConversionResult;
import io.github.bank.common.dto.ExchangeRates;
import io.github.bank.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;
// Аннотация @RestController объявляет класс ExchangeController как компонент контроллера, который обрабатывает
// HTTP-запросы и возвращает данные в формате RESTful.
// Это означает, что методы класса будут автоматически преобразовывать возвращаемые значения в JSON или XML,
// а не конкретный вид представления страницы.
@RestController
// Аннотация @RequestMapping указывает путь, по которому этот контроллер будет доступен.
// В данном случае все обработчики в этом контроллере будут доступны по адресу /exchange.
@RequestMapping("/exchange")
// Аннотация @RequiredArgsConstructor генерирует конструктор, который инициализирует все final поля класса.
// В вашем случае поле rateAdapterService будет проинициализировано через конструктор.
@RequiredArgsConstructor
public class ExchangeController {
//Объявление приватного финального поля rateAdapterService, которое является экземпляром класса ExchangeService.
// Это поле будет использоваться для взаимодействия с сервисом, предоставляющим данные об обменных курсах.
    private final ExchangeService rateAdapterService;
    // Аннотация @GetMapping указывает, что метод getActualExchangeRate
    // будет вызываться при обращении к пути /exchange/actual методом GET.
    @GetMapping("/actual")
    public ExchangeRates getActualExchangeRate(@RequestParam String currency) {
        return rateAdapterService.getActualRates(currency);
    }

    @GetMapping("/history")
    public List<ExchangeRates> getHistoricalExchangeRate(@RequestParam String currency,
                                                         @RequestParam @DateTimeFormat(iso = DATE) LocalDate dateFrom,
                                                         @RequestParam @DateTimeFormat(iso = DATE) LocalDate dateTo) {
        return rateAdapterService.getHistoricalRates(currency, dateFrom, dateTo);
    }

    @GetMapping("/convert")
    public ConversionResult convertRates(@RequestParam String fromCurrency,
                                         @RequestParam String toCurrency,
                                         @RequestParam Double amount) {
        return rateAdapterService.convertRates(fromCurrency, toCurrency, amount);
    }
}

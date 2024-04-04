package io.github.bank.exchange;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


// Это аннотация, которая указывает, что класс ExchangeApplication является основным классом Spring приложения.
// Она объединяет использование аннотаций @Configuration, @EnableAutoConfiguration и @ComponentScan.
@SpringBootApplication
// Это аннотация указывает Spring Framework на включение поддержки клиента Feign.
// Feign - это декларативный HTTP-клиент, который упрощает вызовы удаленных сервисов через HTTP
@EnableFeignClients
//
public class ExchangeApplication {
//Это объявление метода main. В Java точка входа для приложения находится в методе main, и здесь определяется,
// что приложение будет запускаться через вызов SpringApplication.run.
    public static void main(String[] args) {
        //Этот вызов метода run из SpringApplication запускает Spring приложение, используя класс
        // ExchangeApplication в качестве основного класса и аргументы, переданные при запуске программы.
        SpringApplication.run(ExchangeApplication.class, args);
    }
}

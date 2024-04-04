package io.github.bank.exchangerate.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExchangeRateAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateAdapter.class, args);
    }
}

package io.github.bank.exchangerate.adapter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FxRatesApiAdapter {

    public static void main(String[] args) {
        SpringApplication.run(FxRatesApiAdapter.class, args);
    }


}

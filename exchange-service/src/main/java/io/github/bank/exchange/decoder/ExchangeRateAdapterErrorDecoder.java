package io.github.bank.exchange.decoder;


import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.bank.common.dto.BankError;
import io.github.bank.exchange.exception.ExchangeException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
public class ExchangeRateAdapterErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        BankError bankError;

        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

            bankError = mapper.readValue(bodyIs, BankError.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new ExchangeException(response.status(), bankError);
    }
}

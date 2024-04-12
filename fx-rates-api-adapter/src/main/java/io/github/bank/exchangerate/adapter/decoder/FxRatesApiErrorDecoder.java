package io.github.bank.exchangerate.adapter.decoder;


import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import io.github.bank.exchangerate.adapter.dto.FxRatesApiErrorResponse;
import io.github.bank.exchangerate.adapter.exception.FxRatesApiException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Component
public class FxRatesApiErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        FxRatesApiErrorResponse errorResponse;

        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper()
                    .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);

            errorResponse = mapper.readValue(bodyIs, FxRatesApiErrorResponse.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new FxRatesApiException(response.status(), errorResponse);
    }
}

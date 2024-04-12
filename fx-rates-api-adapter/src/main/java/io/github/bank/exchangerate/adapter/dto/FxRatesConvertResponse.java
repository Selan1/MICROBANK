package io.github.bank.exchangerate.adapter.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FxRatesConvertResponse {

    private FxRatesConvertQuery query;
    private FxRatesConvertInfo info;
    private Double result;
}



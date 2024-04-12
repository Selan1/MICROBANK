package io.github.bank.exchangerate.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FxRatesConvertQuery {

    private String from;
    private String to;
    private Double amount;
}



package io.github.bank.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@Table(name = "history_rate")
@AllArgsConstructor
@NoArgsConstructor
public class HistoryRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate actualDate;
    private String currency;
    private Double usdRate;
}

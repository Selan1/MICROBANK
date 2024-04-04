package io.github.bank.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@Table(name = "rate_history_value")
@AllArgsConstructor
@NoArgsConstructor
public class RateHistoryValue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "history_date_id")
    private RateHistoryDate rateHistoryDate;

    private String currency;
    private Double rate;
}

package io.github.bank.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "rate_history_date")
@AllArgsConstructor
@NoArgsConstructor
public class RateHistoryDate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate actualDate;

    @OneToMany
    @JoinColumn(name = "history_date_id")
    private List<RateHistoryValue> rateValues;
}

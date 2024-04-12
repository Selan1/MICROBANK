package io.github.bank.exchange.repository;

import io.github.bank.exchange.entity.HistoryRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RateHistoryDateRepository extends JpaRepository<HistoryRate, Long> {

    List<HistoryRate> findHistoryRateByActualDate(LocalDate date);
}

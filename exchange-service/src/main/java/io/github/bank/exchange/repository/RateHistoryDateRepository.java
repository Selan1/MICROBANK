package io.github.bank.exchange.repository;

import io.github.bank.exchange.entity.RateHistoryDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
//Эта строка объявляет интерфейс RateHistoryDateRepository, который расширяет интерфейс JpaRepository. Интерфейс
// JpaRepository предоставляет базовые методы для работы с базой данных, такие как сохранение,
// обновление, удаление и поиск объектов.
public interface RateHistoryDateRepository extends JpaRepository<RateHistoryDate, Long> {
//Аннотация @Query используется в Spring Data JPA для определения пользовательских запросов к базе данных.
//В данном случае указан запрос на выборку всех записей RateHistoryDate с actualDate больше dateFrom и меньше dateTo.
// Здесь :dateFrom и :dateTo являются параметрами запроса.
    @Query("select rhd from RateHistoryDate rhd where rhd.actualDate > :dateFrom and rhd.actualDate < :dateTo")
    List<RateHistoryDate> findRateHistoryByDates(LocalDate dateFrom, LocalDate dateTo);
}

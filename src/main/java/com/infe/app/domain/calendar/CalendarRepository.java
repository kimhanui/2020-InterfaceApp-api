package com.infe.app.domain.calendar;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    @Query("select c from Calendar c where c.date = :date")
    Optional<Calendar> findByDate(@Param("date")LocalDate date);
}

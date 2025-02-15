package com.otakumap.domain.event.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.entity.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByStartDateAndStatus(LocalDate startDate, EventStatus status);
}

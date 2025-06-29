package com.otakumap.domain.event.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.entity.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
        @Query("SELECT e FROM Event e " +
                "JOIN FETCH e.eventLocation el " +
                "LEFT JOIN FETCH e.eventAnimationList ea " +
                "WHERE el.lat = :latitude AND el.lng = :longitude AND e.endDate >= CURDATE()")
        List<Event> findEventsByLocationWithAnimations(@Param("latitude") Double latitude,
                                                       @Param("longitude") Double longitude);
    List<Event> findByStartDateAndStatus(LocalDate startDate, EventStatus status);
}



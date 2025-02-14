package com.otakumap.domain.event.repository;

import com.otakumap.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
        @Query("SELECT e FROM Event e " +
                "JOIN FETCH e.eventLocation el " +
                "LEFT JOIN FETCH e.eventAnimationList ea " +
                "WHERE el.lat = :latitude AND el.lng = :longitude")
        List<Event> findEventsByLocationWithAnimations(@Param("latitude") Double latitude,
                                                       @Param("longitude") Double longitude);
}



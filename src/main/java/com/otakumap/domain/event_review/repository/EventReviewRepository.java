package com.otakumap.domain.event_review.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {
    Page<EventReview> findAllByEvent(Event event, PageRequest pageRequest);

    @Query("SELECT er.user FROM EventReview er WHERE er.route.id = :routeId")
    Optional<User> findUserByRouteId(@Param("routeId") Long routeId);
}

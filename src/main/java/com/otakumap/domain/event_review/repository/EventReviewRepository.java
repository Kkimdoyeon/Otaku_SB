package com.otakumap.domain.event_review.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event_review.entity.EventReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventReviewRepository extends JpaRepository<EventReview, Long> {
    Page<EventReview> findAllByEvent(Event event, PageRequest pageRequest);
    Optional<EventReview> findByRouteId(Long routeId);
}

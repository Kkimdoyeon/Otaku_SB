package com.otakumap.domain.event_short_review.repository;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event_short_review.entity.EventShortReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventShortReviewRepository extends JpaRepository<EventShortReview, Long> {
    Page<EventShortReview> findAllByEvent(Event event, Pageable pageRequest);
}

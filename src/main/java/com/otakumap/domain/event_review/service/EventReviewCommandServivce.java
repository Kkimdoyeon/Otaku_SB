package com.otakumap.domain.event_review.service;

import com.otakumap.domain.event_review.entity.EventReview;
import org.springframework.data.domain.Page;

public interface EventReviewCommandServivce {
    Page<EventReview> getEventReviews(Long eventId, Integer page);
}

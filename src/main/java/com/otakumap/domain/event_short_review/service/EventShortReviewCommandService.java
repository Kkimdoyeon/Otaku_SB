package com.otakumap.domain.event_short_review.service;

import com.otakumap.domain.event_short_review.dto.EventShortReviewRequestDTO;
import com.otakumap.domain.event_short_review.entity.EventShortReview;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;

public interface EventShortReviewCommandService {
    EventShortReview createEventShortReview(Long eventId, User user, EventShortReviewRequestDTO.NewEventShortReviewDTO request);
    Page<EventShortReview> getEventShortReviewsByEventId(Long eventId, Integer page);
    void updateEventShortReview(Long eventShortReviewId, EventShortReviewRequestDTO.UpdateEventShortReviewDTO request);
    void deleteEventShortReview(Long eventShortReviewId);
}

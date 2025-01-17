package com.otakumap.domain.event_review.service;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.repository.EventRepository;
import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.EventHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventReviewCommandServivceImpl implements EventReviewCommandServivce {

    private final EventRepository eventRepository;
    private final EventReviewRepository eventReviewRepository;

    @Override
    public Page<EventReview> getEventReviews(Long eventId, Integer page) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventHandler(ErrorStatus.EVENT_NOT_FOUND));
        return eventReviewRepository.findAllByEvent(event, PageRequest.of(page, 4));
    }
}

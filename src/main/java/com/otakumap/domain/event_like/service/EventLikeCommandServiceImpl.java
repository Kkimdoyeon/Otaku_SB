package com.otakumap.domain.event_like.service;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event.repository.EventRepository;
import com.otakumap.domain.event_like.converter.EventLikeConverter;
import com.otakumap.domain.event_like.dto.EventLikeRequestDTO;
import com.otakumap.domain.event_like.entity.EventLike;
import com.otakumap.domain.event_like.repository.EventLikeRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.EventHandler;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EventLikeCommandServiceImpl implements EventLikeCommandService {
    private final EventLikeRepository eventLikeRepository;
    private final EventRepository eventRepository;
    private final EntityManager entityManager;

    @Override
    public void addEventLike(User user, Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventHandler(ErrorStatus.EVENT_NOT_FOUND));
        if(eventLikeRepository.existsByUserAndEvent(user, event)) {
            throw new EventHandler(ErrorStatus.EVENT_LIKE_ALREADY_EXISTS);
        }
        eventLikeRepository.save(EventLikeConverter.toEventLike(user, event));
    }

    @Override
    public void deleteEventLike(List<Long> eventIds) {
        eventLikeRepository.deleteAllByIdInBatch(eventIds);
        entityManager.flush();
        entityManager.clear();
    }

    @Override
    public EventLike favoriteEventLike(Long eventLikeId, EventLikeRequestDTO.FavoriteDTO request) {
        EventLike eventLike = eventLikeRepository.findById(eventLikeId).orElseThrow(() -> new EventHandler(ErrorStatus.EVENT_LIKE_NOT_FOUND));
        eventLike.setIsFavorite(request.getIsFavorite());
        return eventLikeRepository.save(eventLike);
    }
}

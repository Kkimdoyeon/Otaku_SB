package com.otakumap.domain.event_like.service;

import com.otakumap.domain.event_like.dto.EventLikeRequestDTO;
import com.otakumap.domain.event_like.entity.EventLike;
import com.otakumap.domain.user.entity.User;

import java.util.List;

public interface EventLikeCommandService {
    void addEventLike(User user, Long eventId);
    void deleteEventLike(List<Long> eventIds, User user);
    EventLike favoriteEventLike(Long eventLikeId, EventLikeRequestDTO.FavoriteDTO request);
}

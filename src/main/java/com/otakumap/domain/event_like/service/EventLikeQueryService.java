package com.otakumap.domain.event_like.service;

import com.otakumap.domain.event_like.dto.EventLikeResponseDTO;
import com.otakumap.domain.user.entity.User;

public interface EventLikeQueryService {
    EventLikeResponseDTO.EventLikePreViewListDTO getEventLikeList(User user, Integer type, Boolean isFavorite, Long lastId, int limit);
    boolean isEventLikeExist(Long id);
}

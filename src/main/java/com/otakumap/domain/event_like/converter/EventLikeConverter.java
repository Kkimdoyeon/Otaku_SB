package com.otakumap.domain.event_like.converter;

import com.otakumap.domain.event.entity.Event;
import com.otakumap.domain.event_like.dto.EventLikeResponseDTO;
import com.otakumap.domain.event_like.entity.EventLike;
import com.otakumap.domain.user.entity.User;

import java.util.List;

public class EventLikeConverter {
    public static EventLikeResponseDTO.EventLikePreViewDTO eventLikePreViewDTO(EventLike eventLike) {
        return EventLikeResponseDTO.EventLikePreViewDTO.builder()
                .id(eventLike.getId())
                .eventId(eventLike.getEvent().getId())
                .name(eventLike.getEvent().getName())
                .thumbnail(eventLike.getEvent().getThumbnailImage().getFileUrl())
                .startDate(eventLike.getEvent().getStartDate())
                .endDate(eventLike.getEvent().getEndDate())
                .isFavorite(eventLike.getIsFavorite())
                .eventType(eventLike.getEvent().getType())
                .build();
    }
    public static EventLikeResponseDTO.EventLikePreViewListDTO eventLikePreViewListDTO(List<EventLikeResponseDTO.EventLikePreViewDTO> eventLikes, boolean hasNext, Long lastId) {
        return EventLikeResponseDTO.EventLikePreViewListDTO.builder()
                .eventLikes(eventLikes)
                .hasNext(hasNext)
                .lastId(lastId)
                .build();
    }

    public static EventLike toEventLike(User user, Event event) {
        return EventLike.builder()
                .event(event)
                .user(user)
                .isFavorite(false)
                .build();
    }

    public static EventLikeResponseDTO.FavoriteResultDTO toFavoriteResultDTO(EventLike eventLike) {
        return EventLikeResponseDTO.FavoriteResultDTO.builder()
                .eventLikeId(eventLike.getId())
                .isFavorite(eventLike.getIsFavorite())
                .build();
    }
}

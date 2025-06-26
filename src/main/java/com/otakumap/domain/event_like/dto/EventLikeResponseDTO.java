package com.otakumap.domain.event_like.dto;

import com.otakumap.domain.event.entity.enums.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class EventLikeResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventLikePreViewDTO {
        Long id;
        Long eventId;
        String name;
        String thumbnail;
        LocalDate startDate;
        LocalDate endDate;
        Boolean isFavorite;
        EventType eventType;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventLikePreViewListDTO {
        List<EventLikeResponseDTO.EventLikePreViewDTO> eventLikes;
        boolean hasNext;
        Long lastId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FavoriteResultDTO {
        Long eventLikeId;
        Boolean isFavorite;
    }
}

package com.otakumap.domain.event_review.dto;

import com.otakumap.domain.image.dto.ImageResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class EventReviewResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventReviewPreViewDTO {
        Long id;
        String title;
        ImageResponseDTO.ImageDTO reviewPhotoUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventReviewPreViewListDTO {
        List<EventReviewResponseDTO.EventReviewPreViewDTO> eventReviews;
        Integer totalPages;
        Integer totalElements;
        Boolean isFirst;
        Boolean isLast;
    }
}

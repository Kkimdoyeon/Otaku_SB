package com.otakumap.domain.place_short_review.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PlaceShortReviewResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceShortReviewListDTO {
        Long placeId;
        String placeName;
        Integer currentPage;
        Integer totalPages;
        List<PlaceShortReviewDTO> shortReviews;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceShortReviewDTO {
        Long id;
        PlaceShortReviewUserDTO user;
        String content;
        Float rating;
        LocalDateTime createdAt;
        Long likes;
        Long dislikes;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceShortReviewUserDTO {
        Long id;
        String nickname;
        // ImageResponseDTO.ImageDTO profileImage;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateReviewDTO {
        private Long reviewId;
        private Float rating;
        private String content;
        private LocalDateTime createdAt;
        private Long placeId;
        private Long placeAnimationId;
    }
}

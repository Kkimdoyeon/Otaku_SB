package com.otakumap.domain.place_short_review.dto;

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
        private Long placeId;
        private String placeName;
        private Integer currentPage;
        private Integer totalPages;
        private List<PlaceShortReviewDTO> shortReviews;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceShortReviewDTO {
        private Long id;
        private PlaceShortReviewUserDTO user;
        private String content;
        private Float rating;
        private LocalDateTime createdAt;
        private Long likes;
        private Long dislikes;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceShortReviewUserDTO {
        private Long userId;
        private String nickname;
        private String profileImage;
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

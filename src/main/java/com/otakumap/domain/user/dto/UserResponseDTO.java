package com.otakumap.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class UserResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoResponseDTO {
        private String profileImageUrl;
        private String nickname;
        private String email;
        private Integer donation;
        private boolean community_activity;
        private boolean event_benefits_info;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserReviewListDTO {
        List<UserReviewDTO> reviews;
        Integer listSize;
        Integer totalPages;
        Long totalElements;
        boolean isFirst;
        boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserReviewDTO {
        private Long reviewId;
        private String reviewType;
        private String title;
        private String content;
        private String thumbnail;
        private Long views;
        private LocalDate createdAt;
    }
}

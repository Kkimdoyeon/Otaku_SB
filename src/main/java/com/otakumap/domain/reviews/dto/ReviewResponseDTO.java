package com.otakumap.domain.reviews.dto;

import com.otakumap.domain.image.dto.ImageResponseDTO;
import com.otakumap.domain.route.dto.RouteResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Top7ReviewPreViewListDTO {
        List<Top7ReviewPreViewDTO> reviews;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Top7ReviewPreViewDTO {
        Long id;
        String title;
        ImageResponseDTO.ImageDTO reviewImage;
        Long view;
        String type;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchedReviewPreViewDTO {
        Long reviewId; // 검색된 Review의 id
        String title;
        String content;
        String type; // "event" 또는 "place"
        Long view;
        LocalDateTime createdAt;
        ImageResponseDTO.ImageDTO reviewImage;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDetailDTO {
        Long reviewId;
        String animationName;
        String title;
        Long view;
        String content;
        List<ImageResponseDTO.ImageDTO> reviewImages;

        String userName;
        ImageResponseDTO.ImageDTO profileImage;
        LocalDateTime createdAt;

        RouteResponseDTO.RouteDTO route;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreatedReviewDTO {
        Long reviewId;
        String title;
        LocalDateTime createdAt;
    }
}

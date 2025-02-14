package com.otakumap.domain.place_review.dto;

import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import com.otakumap.domain.image.dto.ImageResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PlaceReviewResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceReviewDTO {
        private Long reviewId;
        private List<Long> placeIds; // 해령: ids로 수정
        private String title;
        private String content;
        private Long view;
        private LocalDateTime createdAt;
        private ImageResponseDTO.ImageDTO reviewImage;
        String type;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AnimationReviewGroupDTO {
        private Long animationId;
        private String animationName;
        private List<HashTagResponseDTO.HashTagDTO> hashTags;
        private List<PlaceReviewDTO> reviews;
        private long totalReviews;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceAnimationReviewDTO {
        private Long placeId;
        private String placeName;
        private Float avgRating;
        private long totalReviews;
        private List<AnimationReviewGroupDTO> animationGroups;
    }

}

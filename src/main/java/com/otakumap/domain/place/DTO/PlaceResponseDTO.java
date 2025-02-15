package com.otakumap.domain.place.DTO;

import com.otakumap.domain.animation.dto.AnimationResponseDTO;
import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PlaceResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceAnimationListDTO {
        List<PlaceAnimationDTO> placeAnimations;
        int listSize;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceAnimationDTO {
        private Long placeAnimationId;
        private Long animationId;
        private String animationName;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceDetailDTO {
        private Long id;
        private String name;
        private Double latitude;
        private Double longitude;
        private Boolean isFavorite;
        private Boolean isLiked;
        private String animationName;
        private List<HashTagResponseDTO.HashTagDTO> hashtags;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceDTO {
        private Long id;
        private String name;
        private Double latitude;
        private Double longitude;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchedPlaceInfoDTO {
        Long placeId;
        String name;
        List<AnimationResponseDTO.AnimationInfoDTO> animations;
    }
}

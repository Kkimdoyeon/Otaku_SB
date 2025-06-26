package com.otakumap.domain.place_like.dto;

import com.otakumap.domain.animation.dto.AnimationResponseDTO;
import com.otakumap.domain.hash_tag.dto.HashTagResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class PlaceLikeResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceLikePreViewDTO {
        Long id;
        Long placeId;
        String name;
        String detail;
        Double lat;
        Double lng;
        Boolean isFavorite;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceLikePreViewListDTO {
        List<PlaceLikeResponseDTO.PlaceLikePreViewDTO> placeLikes;
        boolean hasNext;
        Long lastId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FavoriteResultDTO {
        Long placeLikeId;
        Boolean isFavorite;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PlaceLikeDetailDTO {
        Long placeLikeId;
        Long placeId;
        String placeName;
        AnimationResponseDTO.AnimationResultDTO animation;
        Double lat;
        Double lng;
        Boolean isLiked;
        List<HashTagResponseDTO.HashTagDTO> hashtags;
    }
}
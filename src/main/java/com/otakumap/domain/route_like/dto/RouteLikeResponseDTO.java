package com.otakumap.domain.route_like.dto;

import com.otakumap.domain.reviews.enums.ReviewType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class RouteLikeResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FavoriteResultDTO {
        Long routeLikeId;
        Boolean isFavorite;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomRouteSaveResultDTO {
        Long routeId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteUpdateResultDTO {
        Long routeId;
        LocalDateTime updatedAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteLikePreViewDTO {
        Long id; // RoutelikeId
        Long routeId;
        String name;
        Long reviewId;
        ReviewType type;
        Boolean isFavorite;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteLikePreViewListDTO {
        List<RouteLikeResponseDTO.RouteLikePreViewDTO> routeLikes;
        boolean hasNext;
        Long lastId;
    }
}

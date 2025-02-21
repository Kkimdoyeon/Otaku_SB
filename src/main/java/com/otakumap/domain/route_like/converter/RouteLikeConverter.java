package com.otakumap.domain.route_like.converter;

import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.route_like.dto.RouteLikeResponseDTO;
import com.otakumap.domain.route_like.entity.RouteLike;
import com.otakumap.domain.user.entity.User;

import java.util.List;

public class RouteLikeConverter {
    public static RouteLike toRouteLike(User user, Route route) {
        return RouteLike.builder()
                .name(route.getName())
                .user(user)
                .route(route)
                .isFavorite(Boolean.FALSE)
                .build();
    }

    public static RouteLikeResponseDTO.FavoriteResultDTO toFavoriteResultDTO(RouteLike routeLike) {
        return RouteLikeResponseDTO.FavoriteResultDTO.builder()
                .routeLikeId(routeLike.getId())
                .isFavorite(routeLike.getIsFavorite())
                .build();
    }

    public static RouteLikeResponseDTO.CustomRouteSaveResultDTO toCustomRouteSaveResultDTO(RouteLike routeLike) {
        return RouteLikeResponseDTO.CustomRouteSaveResultDTO.builder()
                .routeId(routeLike.getRoute().getId())
                .createdAt(routeLike.getCreatedAt())
                .build();
    }

    public static RouteLikeResponseDTO.RouteUpdateResultDTO toRouteUpdateResultDTO(RouteLike routeLike) {
        return RouteLikeResponseDTO.RouteUpdateResultDTO.builder()
                .routeId(routeLike.getRoute().getId())
                .updatedAt(routeLike.getUpdatedAt())
                .build();
    }

    public static RouteLikeResponseDTO.RouteLikePreViewDTO routeLikePreViewDTO(RouteLike routeLike) {
        return RouteLikeResponseDTO.RouteLikePreViewDTO.builder()
                .id(routeLike.getId())
                .routeId(routeLike.getRoute().getId())
                .type(routeLike.getRoute().getEventReview() == null ? ReviewType.PLACE : ReviewType.EVENT)
                .name(routeLike.getRoute().getName())
                .isFavorite(routeLike.getIsFavorite())
                .build();
    }

    public static RouteLikeResponseDTO.RouteLikePreViewListDTO routeLikePreViewListDTO(List<RouteLikeResponseDTO.RouteLikePreViewDTO> routeLikes, boolean hasNext, Long lastId) {
        return RouteLikeResponseDTO.RouteLikePreViewListDTO.builder()
                .routeLikes(routeLikes)
                .hasNext(hasNext)
                .lastId(lastId)
                .build();
    }
}

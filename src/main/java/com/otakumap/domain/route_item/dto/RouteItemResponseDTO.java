package com.otakumap.domain.route_item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class RouteItemResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RouteItemDTO {
        Long routeItemId;
        String name;
        Long placeId;
        Integer itemOrder;
    }
}

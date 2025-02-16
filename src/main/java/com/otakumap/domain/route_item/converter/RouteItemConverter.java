package com.otakumap.domain.route_item.converter;

import com.otakumap.domain.place.entity.Place;
import com.otakumap.domain.route.entity.Route;
import com.otakumap.domain.route_item.dto.RouteItemResponseDTO;
import com.otakumap.domain.route_item.entity.RouteItem;

public class RouteItemConverter {
    public static RouteItem toRouteItem(Integer itemOrder, Place place) {
        return RouteItem.builder()
                .itemOrder(itemOrder)
                .place(place)
                .build();
    }

    public static RouteItem toRouteItem(Integer itemOrder, Route route, Place place) {
        return RouteItem.builder()
                .itemOrder(itemOrder)
                .route(route)
                .place(place)
                .build();
    }

    public static RouteItemResponseDTO.RouteItemDTO toRouteItemDTO(RouteItem routeItem) {
        return RouteItemResponseDTO.RouteItemDTO.builder()
                .routeItemId(routeItem.getId())
                .name(routeItem.getPlace().getName())
                .placeId(routeItem.getPlace().getId())
                .itemOrder(routeItem.getItemOrder())
                .build();
    }
}

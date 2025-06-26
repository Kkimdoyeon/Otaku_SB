package com.otakumap.domain.route.service;

import com.otakumap.domain.route.dto.RouteResponseDTO;
import com.otakumap.domain.user.entity.User;

public interface RouteQueryService {
    boolean isRouteExist(Long routeId);
    RouteResponseDTO.RouteDetailDTO getRouteDetail(User user, Long routeId);
}
package com.otakumap.domain.route_like.service;

import com.otakumap.domain.route_like.dto.RouteLikeResponseDTO;
import com.otakumap.domain.user.entity.User;

public interface RouteLikeQueryService {
    RouteLikeResponseDTO.RouteLikePreViewListDTO getRouteLikeList(User user, Boolean isFavorite, Long lastId, int limit);
    boolean isRouteLikeExist(Long id);
}

package com.otakumap.domain.place.service;

import com.otakumap.domain.mapping.PlaceAnimation;
import com.otakumap.domain.place.DTO.PlaceResponseDTO;
import com.otakumap.domain.user.entity.User;

import java.util.List;

public interface PlaceQueryService {
    List<PlaceAnimation> getPlaceAnimations(Long placeId);
    boolean isPlaceExist(Long placeId);
    PlaceResponseDTO.PlaceDetailDTO getPlaceDetail(User user, Long routeId, Long placeId, Long animationId);
}

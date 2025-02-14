package com.otakumap.domain.map.service;

import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.user.entity.User;

public interface MapCustomService {
    MapResponseDTO.MapDetailDTO findAllMapDetails(Double latitude, Double longitude);
    MapResponseDTO.MapDetailDTO findAllMapDetailsWithFavorite(User user, Double latitude, Double longitude);
}

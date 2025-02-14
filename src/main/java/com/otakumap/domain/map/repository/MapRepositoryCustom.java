package com.otakumap.domain.map.repository;

import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.user.entity.User;

public interface MapRepositoryCustom {
    MapResponseDTO.MapDetailDTO findAllMapDetails(Double latitude, Double longitude);
    MapResponseDTO.MapDetailDTO findAllMapDetailsWithFavorite(User user, Double latitude, Double longitude);
}

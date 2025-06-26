package com.otakumap.domain.map.repository;

import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.user.entity.User;

public interface MapRepositoryCustom {
    MapResponseDTO.MapDetailDTO findAllMapDetails(User user, Double latitude, Double longitude);
}

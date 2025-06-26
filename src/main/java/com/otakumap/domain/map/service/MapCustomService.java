package com.otakumap.domain.map.service;

import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.user.entity.User;

public interface MapCustomService {
    MapResponseDTO.MapDetailDTO findAllMapDetails(User user, Double latitude, Double longitude);
}

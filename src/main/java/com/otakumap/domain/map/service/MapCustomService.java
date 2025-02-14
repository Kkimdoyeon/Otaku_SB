package com.otakumap.domain.map.service;

import com.otakumap.domain.map.dto.MapResponseDTO;

public interface MapCustomService {
    MapResponseDTO.MapDetailDTO findAllMapDetails(Double latitude, Double longitude);
}

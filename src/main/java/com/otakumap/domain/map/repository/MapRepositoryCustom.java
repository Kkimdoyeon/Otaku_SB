package com.otakumap.domain.map.repository;

import com.otakumap.domain.map.dto.MapResponseDTO;

public interface MapRepositoryCustom {
    MapResponseDTO.MapDetailDTO findAllMapDetails(Double latitude, Double longitude);
}

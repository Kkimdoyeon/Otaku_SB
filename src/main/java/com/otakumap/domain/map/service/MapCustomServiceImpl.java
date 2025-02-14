package com.otakumap.domain.map.service;

import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.map.repository.MapRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapCustomServiceImpl implements MapCustomService {

    private final MapRepositoryCustom mapRepositoryCustom;

    @Override
    public MapResponseDTO.MapDetailDTO findAllMapDetails(Double latitude, Double longitude) {
        return mapRepositoryCustom.findAllMapDetails(latitude, longitude);
    }
}
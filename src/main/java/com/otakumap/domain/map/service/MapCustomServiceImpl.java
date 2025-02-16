package com.otakumap.domain.map.service;

import com.otakumap.domain.map.dto.MapResponseDTO;
import com.otakumap.domain.map.repository.MapRepositoryCustom;
import com.otakumap.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class MapCustomServiceImpl implements MapCustomService {
//
//    private final MapRepositoryCustom mapRepositoryCustom;
//
//    @Override
//    public MapResponseDTO.MapDetailDTO findAllMapDetails(User user, Double latitude, Double longitude) {
//        return mapRepositoryCustom.findAllMapDetails(user, latitude, longitude);
//    }
//}
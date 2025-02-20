package com.otakumap.domain.point.service;

import com.otakumap.domain.point.dto.PointResponseDTO;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.point.repository.PointRepository;
import com.otakumap.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointQueryserviceImpl implements PointQueryservice {
    private final PointRepository pointRepository;

    @Override
    public Page<Point> getChargePointList(User user, Integer page) {
        return pointRepository.findAllByUser(user, PageRequest.of(page - 1, 6));
    }

    @Override
    public PointResponseDTO.CurrentPointDTO getCurrentPoint(User user) {
        return PointResponseDTO.CurrentPointDTO.builder()
                .userId(user.getUserId())
                .point(user.getTotalPoint()) // 유저 테이블의 totalPoint 값을 반환
                .build();
    }
}
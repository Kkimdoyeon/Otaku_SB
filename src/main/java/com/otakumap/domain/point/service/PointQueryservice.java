package com.otakumap.domain.point.service;

import com.otakumap.domain.point.dto.PointResponseDTO;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;

public interface PointQueryservice {
    Page<Point> getChargePointList(User user, Integer page);
    PointResponseDTO.CurrentPointDTO getCurrentPoint(User user);
}

package com.otakumap.domain.point.converter;

import com.otakumap.domain.point.dto.PointResponseDTO;
import com.otakumap.domain.point.entity.Point;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class PointConverter {
    public static PointResponseDTO.PointPreViewDTO pointPreViewDTO(Point point){
        return PointResponseDTO.PointPreViewDTO.builder()
                .chargedBy(point.getChargedBy())
                .point(point.getPoint())
                .chargedAt(point.getChargedAt())
                .build();
    }
    public static PointResponseDTO.PointPreViewListDTO pointPreViewListDTO(Page<Point> pointList) {
        List<PointResponseDTO.PointPreViewDTO> pointPreViewDTOList = pointList.stream()
                .map(PointConverter::pointPreViewDTO).collect(Collectors.toList());

        return PointResponseDTO.PointPreViewListDTO.builder()
                .isLast(pointList.isLast())
                .isFirst(pointList.isFirst())
                .totalPage(pointList.getTotalPages())
                .totalElements(pointList.getTotalElements())
                .listSize(pointPreViewDTOList.size())
                .pointList(pointPreViewDTOList)
                .build();
    }
}

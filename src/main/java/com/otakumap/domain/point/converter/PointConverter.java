package com.otakumap.domain.point.converter;

import com.otakumap.domain.payment.entity.UserPayment;
import com.otakumap.domain.payment.enums.PaymentStatus;
import com.otakumap.domain.point.dto.PointResponseDTO;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.user.entity.User;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
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

//    public static Point toPoint(User user, BigDecimal point) {
//        return Point.builder()
//                .user(user)
//                .point(point)
//                .build();
//    }

    public static Point savePoint(User user, Long point, UserPayment userPayment) {
        return Point.builder()
                .user(user)
                .point(point)
                .chargedAt(LocalDateTime.now()) // 현재 시간
                .chargedBy(user.getName()) // 충전한 사용자
                .status(PaymentStatus.PAID) // 결제 완료 상태
                .userPayment(userPayment) // 결제 정보 추가
                .build();
    }
}

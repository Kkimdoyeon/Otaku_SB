package com.otakumap.domain.point.dto;

import com.otakumap.domain.payment.enums.PaymentStatus;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class PointResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PointPreViewListDTO {
        List<PointPreViewDTO> pointList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PointPreViewDTO {
        String chargedBy;
        Long point;
        LocalDateTime chargedAt;
    }


    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrentPointDTO {
        String userId;
        Long point;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PointSaveResponseDTO {
        private String impUid;
        private String merchantUid;
        private Long point;
        private String chargedBy;
        private PaymentStatus status;
        private LocalDateTime chargedAt;

        public Point toEntity(User user) {
            return Point.builder()
                    .user(user)
                    .point(point)
                    .impUid(impUid)
                    .merchantUid(merchantUid)
                    .chargedBy(chargedBy)
                    .status(status != null ? status : PaymentStatus.PENDING) // 기본값 설정
                    .chargedAt(chargedAt != null ? chargedAt : LocalDateTime.now()) // 기본값 설정
                    .build();
        }

        public String getImpUid() {
            return impUid;
        }
    }
}

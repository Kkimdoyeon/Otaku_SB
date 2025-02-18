package com.otakumap.domain.point.service;

import com.otakumap.domain.payment.enums.PaymentStatus;
import com.otakumap.domain.payment.repository.PaymentRepository;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.point.repository.PointRepository;
import com.otakumap.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PointCommandServiceImpl implements PointCommandService {

    private final PaymentRepository paymentRepository;
    private final PointRepository pointRepository;

    @Override
    public void chargePoints(User user, Long point) {
        // 랜덤 merchantUid 생성 (중복 방지)
        String merchantUid;
        do {
            merchantUid = UUID.randomUUID().toString();
        } while (paymentRepository.existsByMerchantUid(merchantUid));

        // 포인트 충전 내역 저장
        Point pointRecord = Point.builder()
                .user(user)
                .point(point)
                .merchantUid(merchantUid)
                .chargedBy(user.getName())
                .chargedAt(LocalDateTime.now())
                .status(PaymentStatus.PAID)
                .build();

        pointRepository.save(pointRecord);
    }
}

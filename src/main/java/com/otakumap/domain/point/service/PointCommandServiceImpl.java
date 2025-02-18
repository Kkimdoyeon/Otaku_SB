package com.otakumap.domain.point.service;

import com.otakumap.domain.payment.entity.UserPayment;
import com.otakumap.domain.payment.repository.PaymentRepository;
import com.otakumap.domain.point.converter.PointConverter;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.point.repository.PointRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.PaymentHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PointCommandServiceImpl implements PointCommandService {

    private final PaymentRepository paymentRepository;
    private final PointRepository pointRepository;

    @Override
    public void chargePoints(User user, Long point, String merchantUid) {

        if (paymentRepository.existsByMerchantUid(merchantUid)) {
            throw new PaymentHandler(ErrorStatus.PAYMENT_DUPLICATE);
        }

        // UserPayment 조회
        UserPayment userPayment = paymentRepository.findByMerchantUid(merchantUid)
                .orElseThrow(() -> new PaymentHandler(ErrorStatus.PAYMENT_NOT_FOUND));

        // 포인트 충전 내역 저장
        Point pointRecord = PointConverter.savePoint(user, point, userPayment);
        pointRepository.save(pointRecord);
    }
}

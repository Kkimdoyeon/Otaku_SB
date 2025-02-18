package com.otakumap.domain.payment.service;

import com.otakumap.domain.payment.dto.PaymentVerifyRequest;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.point.repository.PointRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.PaymentHandler;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.siot.IamportRestClient.IamportClient;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final IamportClient iamportClient;
    private final PointRepository pointRepository;

    @Transactional
    public void verifyPayment(User user, PaymentVerifyRequest request) throws IOException, IamportResponseException {
        // 1. 아임포트 결제 정보 조회
        IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(request.getImpUid());
        Payment payment = paymentResponse.getResponse();

        if (payment == null) {
            throw new PaymentHandler(ErrorStatus.PAYMENT_NOT_FOUND);
        }

        // 2. 결제 상태 확인
        if (!"paid".equals(payment.getStatus())) {
            throw new PaymentHandler(ErrorStatus.PAYMENT_STATUS_INVALID);
        }

        // 3. 결제 금액 검증
        if (!payment.getAmount().equals(request.getAmount())) {
            throw new PaymentHandler(ErrorStatus.PAYMENT_AMOUNT_MISMATCH);
        }

        // 4. 중복 결제 방지
        if (pointRepository.findByMerchantUid(request.getMerchantUid()).isPresent()) {
            throw new PaymentHandler(ErrorStatus.PAYMENT_DUPLICATE);
        }

        // 5. 포인트 저장
        Point point = new Point(user, request.getMerchantUid(), request.getAmount());
        pointRepository.save(point);
    }
}
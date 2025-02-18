package com.otakumap.domain.payment.service;

import com.otakumap.domain.payment.dto.PaymentVerifyRequest;
import com.otakumap.domain.payment.entity.UserPayment;
import com.otakumap.domain.payment.enums.PaymentStatus;
import com.otakumap.domain.payment.repository.PaymentRepository;
import com.otakumap.domain.point.converter.PointConverter;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.point.repository.PointRepository;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.PaymentHandler;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final IamportClient iamportClient;
    private final PointRepository pointRepository;
    private final PaymentRepository paymentRepository;
    //private final PointConverter pointConverter;

    @Transactional
    public void verifyPayment(User user, PaymentVerifyRequest request) throws IOException, IamportResponseException {
        // 아임포트 결제 정보 조회
        IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(request.getImpUid());
        Payment payment = paymentResponse.getResponse();

        if (payment == null) {
            throw new PaymentHandler(ErrorStatus.PAYMENT_NOT_FOUND);
        }

        // 결제 상태 확인
        if (!"paid".equals(payment.getStatus())) {
            throw new PaymentHandler(ErrorStatus.PAYMENT_STATUS_INVALID);
        }

        // 결제 금액 검증
        if (!payment.getAmount().equals(request.getAmount())) {
            throw new PaymentHandler(ErrorStatus.PAYMENT_AMOUNT_MISMATCH);
        }

        // 중복 결제 방지
        if (paymentRepository.findByMerchantUid(request.getMerchantUid()).isPresent()) {
            throw new PaymentHandler(ErrorStatus.PAYMENT_DUPLICATE);
        }

        // 결제 정보 저장
        UserPayment userPayment = UserPayment.builder()
                .user(user)
                .impUid(payment.getImpUid())
                .merchantUid(payment.getMerchantUid())
                .amount(payment.getAmount().longValue())
                .verifiedAt(LocalDateTime.now())
                .status(PaymentStatus.PAID)
                .build();

        paymentRepository.save(userPayment);

        System.out.println("✅ Payment 정보: " + payment);
        System.out.println("✅ UserPayment 정보: " + userPayment);

        // 포인트 저장
        Point point = new Point(
                Long.valueOf(String.valueOf(payment.getAmount())),               // 충전된 포인트
                LocalDateTime.now(),               // 충전 시간
                PaymentStatus.PAID,               // 상태 설정
                user,                              // 사용자 정보
                userPayment                            // 결제 정보
        );

        System.out.println("✅ 생성된 Point 정보: " + point);

        pointRepository.save(point); // 포인트 저장

        // 결제 정보와 포인트 정보를 연결
        userPayment.setPoint(point);
        paymentRepository.save(userPayment);
    }
}
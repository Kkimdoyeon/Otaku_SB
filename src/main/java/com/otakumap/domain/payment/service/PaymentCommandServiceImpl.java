package com.otakumap.domain.payment.service;

import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.domain.order.dto.OrderDto;
import com.otakumap.domain.order.repository.OrderRepository;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.domain.point.converter.PointConverter;
import com.otakumap.domain.point.entity.Point;
import com.otakumap.domain.point.repository.PointRepository;
import com.otakumap.domain.transaction.repository.TransactionRepository;
import com.otakumap.domain.user.entity.User;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final IamportClient iamportClient;
    private final OrderRepository orderRepository;
    private final PointRepository pointRepository;

    /**
     * 아임포트 서버로부터 결제 정보를 검증
     * @param imp_uid
     */
    public IamportResponse<Payment> validateIamport(String imp_uid) {
        try {
            IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
            log.info("결제 요청 응답. 결제 내역 - 주문 번호: {}", payment.getResponse());
            return payment;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    /**
     * 아임포트 서버로부터 결제 취소 요청
     *
     * @param imp_uid
     * @return
     */
    public IamportResponse<Payment> cancelPayment(String imp_uid) {
        try {
            CancelData cancelData = new CancelData(imp_uid, true);
            IamportResponse<Payment> payment = iamportClient.cancelPaymentByImpUid(cancelData);
            return payment;
        } catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    /**
     * 주문 정보 저장
     * @param orderDto
     * @return
     */
    public String saveOrder(OrderDto orderDto, User user){
        try {
            // 주문 정보 저장
            orderRepository.save(orderDto.toEntity());

            // 사용자에 해당하는 모든 Point 객체 조회
            List<Point> existingPoints = pointRepository.findByUserId(user.getId());

            // 기존 포인트가 없다면 새로 생성하여 저장
            if (existingPoints.isEmpty()) {
                PointConverter.savePoint(orderDto, user);
            } else {
                // 여러 포인트 객체가 있을 때 합산
                Long totalPoint = existingPoints.stream()
                        .mapToLong(Point::getPoint)
                        .sum(); // 여러 포인트 합산

                // 새로 받은 포인트 합산
                totalPoint += orderDto.getPrice();

                // 첫 번째 Point 객체에 업데이트하거나, 새로 포인트 객체를 만들어서 저장
                Point updatedPoint = existingPoints.get(0); // 기존 첫 번째 Point 객체 선택
                updatedPoint.setPoint(totalPoint); // 합산된 포인트로 업데이트
                updatedPoint.setChargedAt(LocalDateTime.now());
                pointRepository.save(updatedPoint);
            }
            return "주문 정보가 성공적으로 저장되었습니다.";
        } catch (Exception e) {
            log.info(e.getMessage());
            cancelPayment(orderDto.getImpUid());
            return "주문 정보 저장에 실패했습니다.";
        }
    }
}
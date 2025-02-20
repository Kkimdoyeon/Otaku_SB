package com.otakumap.domain.payment.service;

import com.otakumap.domain.point.dto.PointResponseDTO;
import com.otakumap.domain.user.entity.User;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import org.springframework.stereotype.Service;

@Service
public interface PaymentCommandService {
    IamportResponse<Payment> validateIamport(String imp_uid);
    IamportResponse<Payment> cancelPayment(String imp_uid);
    String savePoint(PointResponseDTO.PointSaveResponseDTO pointResponseDTO, User user);
}

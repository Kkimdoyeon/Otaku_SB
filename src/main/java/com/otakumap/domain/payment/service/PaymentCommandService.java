package com.otakumap.domain.payment.service;

import com.otakumap.domain.payment.dto.PaymentVerifyRequest;
import com.otakumap.domain.user.entity.User;
import com.siot.IamportRestClient.exception.IamportResponseException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface PaymentCommandService {
    void verifyPayment(User user, PaymentVerifyRequest request) throws IamportResponseException, IOException;
}

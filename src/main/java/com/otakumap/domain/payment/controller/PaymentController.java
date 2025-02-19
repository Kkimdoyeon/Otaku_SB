package com.otakumap.domain.payment.controller;

import com.otakumap.domain.payment.service.PaymentCommandService;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Slf4j
@Validated
@Tag(name = "Payments", description = "결제 API")
public class PaymentController {

    private final PaymentCommandService paymentCommandService;

    @Operation(summary = "아임포트 결제 정보 검증", description = "아임포트 결제 정보를 검증합니다.")
    @PostMapping("/verify/{imp_uid}")
    public IamportResponse<Payment> validateIamport(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        log.info("imp_uid: {}", imp_uid);
        log.info("validateIamport");
        return paymentCommandService.validateIamport(imp_uid);
    }

    @Operation(summary = "결제 취소", description = "결제를 취소합니다.")
    @PostMapping("/cancel/{imp_uid}")
    public IamportResponse<Payment> cancelPayment(@PathVariable String imp_uid) throws IamportResponseException, IOException {
        return paymentCommandService.cancelPayment(imp_uid);
    }
}

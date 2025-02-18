package com.otakumap.domain.payment.controller;

import com.otakumap.domain.auth.jwt.annotation.CurrentUser;
import com.otakumap.domain.payment.dto.PaymentVerifyRequest;
import com.otakumap.domain.payment.service.PaymentCommandService;
import com.otakumap.domain.user.entity.User;
import com.otakumap.global.apiPayload.ApiResponse;
import com.siot.IamportRestClient.exception.IamportResponseException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PaymentController {

    private final PaymentCommandService paymentCommandService;

    @Operation(summary = "결제 검증", description = "결제가 제대로 진행됐는지 검증합니다.")
    @PostMapping("/verify")
    public ApiResponse<String> verifyPayment(
            @Valid @RequestBody PaymentVerifyRequest request,
            @CurrentUser User user
    ) throws IamportResponseException, IOException {

        // 결제 검증 서비스 호출
        paymentCommandService.verifyPayment(user, request);

        // 성공적인 응답 반환
        return ApiResponse.onSuccess("결제가 검증되었습니다.");
    }
}

package com.otakumap.domain.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentVerifyRequest {
    @NotNull(message = "imp_uid는 필수입니다.")
    private String impUid;

    @NotNull(message = "merchant_uid는 필수입니다.")
    private String merchantUid;

    @NotNull(message = "amount는 필수입니다.")
    private Long amount;
}

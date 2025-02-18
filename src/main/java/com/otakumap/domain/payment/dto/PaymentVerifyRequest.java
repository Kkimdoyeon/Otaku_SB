package com.otakumap.domain.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PaymentVerifyRequest {
    @NotNull(message = "imp_uid는 필수입니다.")
    String impUid;
    @NotNull(message = "merchant_uid는 필수입니다.")
    String merchantUid;
    @NotNull(message = "amount는 필수입니다.")
    Long amount;
}

package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class PaymentHandler extends GeneralException {
    public PaymentHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

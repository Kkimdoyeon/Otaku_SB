package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class TransactionHandler extends GeneralException {
    public TransactionHandler(BaseErrorCode code) {
        super(code);
    }
}

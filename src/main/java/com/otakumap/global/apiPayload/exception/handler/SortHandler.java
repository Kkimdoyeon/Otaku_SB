package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class SortHandler extends GeneralException {
    public SortHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

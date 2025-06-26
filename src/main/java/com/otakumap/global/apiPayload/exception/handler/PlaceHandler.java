package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class PlaceHandler extends GeneralException {
    public PlaceHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

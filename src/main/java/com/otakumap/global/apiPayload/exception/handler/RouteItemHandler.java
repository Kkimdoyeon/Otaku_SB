package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class RouteItemHandler extends GeneralException {
    public RouteItemHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

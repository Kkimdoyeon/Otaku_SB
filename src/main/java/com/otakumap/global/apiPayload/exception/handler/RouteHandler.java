package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class RouteHandler extends GeneralException {
    public RouteHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

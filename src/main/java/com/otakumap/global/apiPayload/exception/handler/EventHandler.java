package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class EventHandler extends GeneralException {
    public EventHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

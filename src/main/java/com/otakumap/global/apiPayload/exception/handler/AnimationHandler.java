package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class AnimationHandler extends GeneralException {
    public AnimationHandler(BaseErrorCode errorCode) { super(errorCode); }
}
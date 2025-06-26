package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class ImageHandler extends GeneralException {
    public ImageHandler(BaseErrorCode errorCode) { super(errorCode); }
}

package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class SearchHandler extends GeneralException  {
    public SearchHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}

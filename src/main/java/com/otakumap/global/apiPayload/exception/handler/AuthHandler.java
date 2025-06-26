package com.otakumap.global.apiPayload.exception.handler;

import com.otakumap.global.apiPayload.code.BaseErrorCode;
import com.otakumap.global.apiPayload.exception.GeneralException;

public class AuthHandler extends GeneralException {
  public AuthHandler(BaseErrorCode errorCode) {
    super(errorCode);
  }
}

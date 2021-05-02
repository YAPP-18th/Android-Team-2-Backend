package com.sns.zuzuclub.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeType {

  INVALID_SOCIAL_PROVIDER(420, "유효하지 않은 로그인 제공자"),
  INVALID_SOCIAL_TOKEN(421, "유효하지 않은 로그인 토큰");


  private final int errorCode;
  private final String message;

  ErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

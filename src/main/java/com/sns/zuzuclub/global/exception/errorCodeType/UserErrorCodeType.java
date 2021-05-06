package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum UserErrorCodeType {

  INVALID_USER(431, "유효하지 않은 유저"),
  INVALID_REFRESH_TOKEN(432, "유효하지 않은 재발급 토큰");

  private final int errorCode;
  private final String message;

  UserErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

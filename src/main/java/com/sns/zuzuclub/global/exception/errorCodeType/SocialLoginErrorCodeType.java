package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum SocialLoginErrorCodeType {

  INVALID_SOCIAL_PROVIDER(410, "유효하지 않은 로그인 제공자"),
  FAILED_SOCIAL_TOKEN_VALIDATION_CHECK(411, "소셜토큰 제공자로부터 유효성 검증 실패");

  private final int errorCode;
  private final String message;

  SocialLoginErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

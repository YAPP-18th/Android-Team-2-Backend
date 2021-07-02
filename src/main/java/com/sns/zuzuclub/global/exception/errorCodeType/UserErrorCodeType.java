package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum UserErrorCodeType {

  INVALID_USER(431, "유효하지 않은 유저"),
  INVALID_REFRESH_TOKEN(432, "유효하지 않은 재발급 토큰"),
  DUPLICATE_NICKNAME(433, "중복된 닉네임"),
  EMPTY_NICKNAME(434, "빈 문자열 닉네임"),
  INVALID_FOLLOW_INFO(435, "유효하지 않은 팔로우 정보");

  private final int errorCode;
  private final String message;

  UserErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

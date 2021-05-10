package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum JwtErrorCodeType {

  JWT_TOKEN_ERROR(421, "JWT 토큰 에러"),
  UNAUTHORIZED_JWT(422, "JWT 토큰에 유효하지 않은 권한"),
  EXPIRED_JWT_TOKEN(423, "만료된 JWT 토큰"),
  UNSUPPORTED_JWT_TOKEN(424, "지원되지 않는 JWT 토큰"),
  MALFORMED_JWT_TOKEN(425, "잘못된 JWT 토큰"),
  NOT_MATCH_FROM_USER_ID(426, "Refresh Token 정보가 일치하지 않습니다.");

  private final int errorCode;
  private final String message;

  JwtErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

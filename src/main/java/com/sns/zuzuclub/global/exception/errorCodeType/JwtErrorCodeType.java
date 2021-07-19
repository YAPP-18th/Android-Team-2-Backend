package com.sns.zuzuclub.global.exception.errorCodeType;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@JsonFormat(shape = Shape.OBJECT)
@Getter
public enum JwtErrorCodeType {

  EXPIRED_ACCESS_TOKEN(421, "만료된 ACC 토큰"),
  EXPIRED_REFRESH_TOKEN(422, "만료된 REF 토큰"),
  NOT_VALID_TOKEN(423, "유효하지 않은 토큰"),
  UNAUTHORIZED_JWT(424, "허용되지 않은 권한");


  @JsonProperty("code")
  private final int errorCode;
  @JsonProperty("msg")
  private final String message;

  JwtErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

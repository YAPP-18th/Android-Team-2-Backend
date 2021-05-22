package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum HomeErrorCodeType {

  EMPTY_WEATHER_DATA(471, "날씨 데이터가 존재하지 않음");


  private final int errorCode;
  private final String message;

  HomeErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

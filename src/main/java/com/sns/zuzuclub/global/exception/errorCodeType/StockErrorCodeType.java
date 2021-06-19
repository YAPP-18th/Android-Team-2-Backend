package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum StockErrorCodeType {
  INVALID_STOCK_ID(481, "유효하지 않은 주식 종목 id"),
  STOCK_EMOTION_UPDATE_ERROR(482, "주식 종목 감정 계산 오류")
  ;

  private final int errorCode;
  private final String message;

  StockErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

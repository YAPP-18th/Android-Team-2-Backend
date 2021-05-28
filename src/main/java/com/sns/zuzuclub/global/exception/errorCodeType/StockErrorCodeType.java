package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum StockErrorCodeType {
  INVALID_STOCK_ID(481, "유효하지 않은 주식 종목 id"),
  ;

  private final int errorCode;
  private final String message;

  StockErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

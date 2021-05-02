package com.sns.zuzuclub.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

  private final int errorCode;

  public CustomException(ErrorCodeType errorCodeType)
  {
    super(errorCodeType.getMessage());
    this.errorCode = errorCodeType.getErrorCode();
  }
}

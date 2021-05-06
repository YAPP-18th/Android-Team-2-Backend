package com.sns.zuzuclub.global.exception;

import com.sns.zuzuclub.global.exception.errorCodeType.SocialLoginErrorCodeType;
import com.sns.zuzuclub.global.exception.errorCodeType.JwtErrorCodeType;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

  private final int errorCode;

  public CustomException(SocialLoginErrorCodeType socialLoginErrorCodeType)
  {
    super(socialLoginErrorCodeType.getMessage());
    this.errorCode = socialLoginErrorCodeType.getErrorCode();
  }

  public CustomException(JwtErrorCodeType errorCodeType)
  {
    super(errorCodeType.getMessage());
    this.errorCode = errorCodeType.getErrorCode();
  }

  public CustomException(UserErrorCodeType errorCodeType)
  {
    super(errorCodeType.getMessage());
    this.errorCode = errorCodeType.getErrorCode();
  }
}

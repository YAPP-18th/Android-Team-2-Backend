package com.sns.zuzuclub.global.exception;

import com.sns.zuzuclub.global.exception.errorCodeType.CommentErrorCodeType;
import com.sns.zuzuclub.global.exception.errorCodeType.ImageUploadErrorCodeType;
import com.sns.zuzuclub.global.exception.errorCodeType.PostErrorCodeType;
import com.sns.zuzuclub.global.exception.errorCodeType.SocialLoginErrorCodeType;
import com.sns.zuzuclub.global.exception.errorCodeType.JwtErrorCodeType;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

  // enum 을 야매 상속(인터페이스 활용)으롷 리팩토링

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

  public CustomException(PostErrorCodeType errorCodeType)
  {
    super(errorCodeType.getMessage());
    this.errorCode = errorCodeType.getErrorCode();
  }

  public CustomException(CommentErrorCodeType errorCodeType)
  {
    super(errorCodeType.getMessage());
    this.errorCode = errorCodeType.getErrorCode();
  }

  public CustomException(ImageUploadErrorCodeType errorCodeType)
  {
    super(errorCodeType.getMessage());
    this.errorCode = errorCodeType.getErrorCode();
  }

}

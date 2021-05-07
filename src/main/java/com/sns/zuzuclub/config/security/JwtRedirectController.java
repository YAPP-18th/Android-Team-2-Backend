package com.sns.zuzuclub.config.security;

import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.JwtErrorCodeType;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/exception")
@RestController
public class JwtRedirectController {

  @GetMapping(value = "/entrypoint")
  public void entryPointException() {
    throw new CustomException(JwtErrorCodeType.JWT_TOKEN_ERROR);
  }

  @GetMapping(value = "/denied")
  public void accessDeniedException() {
    throw new CustomException(JwtErrorCodeType.UNAUTHORIZED_JWT);
  }

  @GetMapping(value = "/failure")
  public void AuthenticationFailureException() {
    throw new CustomException(UserErrorCodeType.INVALID_USER);
  }

}

package com.sns.zuzuclub.controller.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {

  private String jwtAccessToken;
  private String jwtRefreshToken;
  private boolean needUserInfo;

  @Builder
  public LoginResponseDto(String jwtAccessToken, String jwtRefreshToken, boolean needUserInfo) {
    this.jwtAccessToken = jwtAccessToken;
    this.jwtRefreshToken = jwtRefreshToken;
    this.needUserInfo = needUserInfo;
  }
}

package com.sns.zuzuclub.controller.login.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class LoginResponseDto {

  @ApiModelProperty(value = "JWT - AccessToken")
  private String jwtAccessToken;

  @ApiModelProperty(value = "JWT - RefreshToken")
  private String jwtRefreshToken;

  @ApiModelProperty(value = "회원가입 진행해야하는지 여부")
  private boolean needUserInfo;

  @Builder
  public LoginResponseDto(String jwtAccessToken, String jwtRefreshToken, boolean needUserInfo) {
    this.jwtAccessToken = jwtAccessToken;
    this.jwtRefreshToken = jwtRefreshToken;
    this.needUserInfo = needUserInfo;
  }
}

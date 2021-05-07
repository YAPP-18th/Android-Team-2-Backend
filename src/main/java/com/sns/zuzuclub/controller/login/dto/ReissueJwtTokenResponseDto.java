package com.sns.zuzuclub.controller.login.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueJwtTokenResponseDto {
  @ApiModelProperty(value = "JWT - AccessToken")
  private String jwtAccessToken;
  @ApiModelProperty(value = "RefreshToken 이 포함되어있는지 알려줍니다.")
  private boolean hasRefreshToken;
  @ApiModelProperty(value = "JWT - RefreshToken")
  private String jwtRefreshToken;

  @Builder
  public ReissueJwtTokenResponseDto(String jwtAccessToken, boolean hasRefreshToken,
      String jwtRefreshToken) {
    this.jwtAccessToken = jwtAccessToken;
    this.hasRefreshToken = hasRefreshToken;
    this.jwtRefreshToken = jwtRefreshToken;
  }
}

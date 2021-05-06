package com.sns.zuzuclub.controller.login.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReissueJwtTokenResponseDto {
  private String jwtAccessToken;
  private boolean hasRefreshToken;
  private String jwtRefreshToken;

  @Builder
  public ReissueJwtTokenResponseDto(String jwtAccessToken, boolean hasRefreshToken,
      String jwtRefreshToken) {
    this.jwtAccessToken = jwtAccessToken;
    this.hasRefreshToken = hasRefreshToken;
    this.jwtRefreshToken = jwtRefreshToken;
  }
}

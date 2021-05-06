package com.sns.zuzuclub.controller.login.dto;

import lombok.Getter;

@Getter
public class ReissueJwtTokenRequestDto {
  private String jwtRefreshToken;
}

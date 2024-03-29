package com.sns.zuzuclub.domain.user.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ReissueJwtTokenResponseDto {
  @ApiModelProperty(value = "JWT - AccessToken")
  private String jwtAccessToken;
  @ApiModelProperty(value = "JWT - RefreshToken")
  private String jwtRefreshToken;

  @Builder
  public ReissueJwtTokenResponseDto(String jwtAccessToken, String jwtRefreshToken) {
    this.jwtAccessToken = jwtAccessToken;
    this.jwtRefreshToken = jwtRefreshToken;
  }
}

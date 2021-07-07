package com.sns.zuzuclub.domain.user.dto.login;

import com.sns.zuzuclub.config.security.social.SocialTokenProviderType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LoginRequestDto {

  @ApiModelProperty(value = "소셜로그인 제공자로부터 받은 AccessToken", example = "토큰을넣어주세요")
  private String socialToken;

  @ApiModelProperty(value = "소셜로그인 제공자", example = "NAVER")
  private SocialTokenProviderType provider;
}

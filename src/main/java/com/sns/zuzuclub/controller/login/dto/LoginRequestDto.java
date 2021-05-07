package com.sns.zuzuclub.controller.login.dto;

import com.sns.zuzuclub.constant.SocialTokenProviderType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class LoginRequestDto {

  @ApiModelProperty(value = "소셜로그인 제공자로부터 받은 AccessToken", example = "토큰을넣어주세요")
  private String socialToken;

  @ApiModelProperty(value = "소셜로그인 제공자", example = "NAVER")
  private SocialTokenProviderType provider;
}

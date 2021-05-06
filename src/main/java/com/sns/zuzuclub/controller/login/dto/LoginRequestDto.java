package com.sns.zuzuclub.controller.login.dto;

import com.sns.zuzuclub.constant.SocialTokenProviderType;
import lombok.Getter;

@Getter
public class LoginRequestDto {

  private String socialToken;
  private SocialTokenProviderType provider;
}

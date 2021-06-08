package com.sns.zuzuclub.controller.profile.dto;

import com.sns.zuzuclub.domain.user.model.User;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ProfileSettingDto {

  private final String profileImageUrl;
  private final String nickname;
  private final String introduction;

  public ProfileSettingDto(User user) {
    this.profileImageUrl = user.getProfileImageUrl();
    this.nickname = user.getNickname();
    this.introduction = user.getIntroduction();
  }

}

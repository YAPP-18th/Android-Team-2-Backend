package com.sns.zuzuclub.controller.profile.dto;

import com.sns.zuzuclub.domain.user.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class ProfileSettingDto {

  private String profileImageUrl;
  private String nickname;
  private String introduction;

  public ProfileSettingDto(User user) {
    this.profileImageUrl = user.getProfileImageUrl();
    this.nickname = user.getNickname();
    this.introduction = user.getIntroduction();
  }

}

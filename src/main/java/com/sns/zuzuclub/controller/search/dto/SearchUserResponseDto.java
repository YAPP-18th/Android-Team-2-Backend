package com.sns.zuzuclub.controller.search.dto;

import com.sns.zuzuclub.domain.user.model.UserInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class SearchUserResponseDto {

  private Long userId;

  private String nickname;

  private String profileImageUrl;

  public SearchUserResponseDto(UserInfo userInfo) {
    this.userId = userInfo.getId();
    this.nickname = userInfo.getNickname();
    this.profileImageUrl = userInfo.getProfileImageUrl();
  }

  public static List<SearchUserResponseDto> toListFrom(List<UserInfo> userInfoList){
    return userInfoList.stream()
                       .map(SearchUserResponseDto::new)
                       .collect(Collectors.toList());
  }
}

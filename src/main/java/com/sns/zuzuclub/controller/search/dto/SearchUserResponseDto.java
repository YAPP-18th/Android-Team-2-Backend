package com.sns.zuzuclub.controller.search.dto;

import com.sns.zuzuclub.domain.user.model.User;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SearchUserResponseDto {

  private Long userId;

  private String nickname;

  private String profileImageUrl;

  public SearchUserResponseDto(User user) {
    this.userId = user.getId();
    this.nickname = user.getNickname();
    this.profileImageUrl = user.getProfileImageUrl();
  }

  public static List<SearchUserResponseDto> toListOf(List<User> userList){
    return userList.stream()
                       .map(SearchUserResponseDto::new)
                       .collect(Collectors.toList());
  }
}

package com.sns.zuzuclub.controller.follow.dto;

import com.sns.zuzuclub.domain.user.model.User;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class FollowDto {

  @ApiModelProperty(value = "유저id")
  private Long userId;

  @ApiModelProperty(value = "프로필 이미지 url")
  private String profileImageUrl;

  @ApiModelProperty(value = "유저 닉네임")
  private String nickname;

  @ApiModelProperty(value = "로그인 유저가 해당 유저를 팔로우하고 있는지 여부")
  private boolean isloginUserFollow;

  public FollowDto(User loginUser, User resultUser) {
    this.userId = resultUser.getId();
    this.profileImageUrl = resultUser.getProfileImageUrl();
    this.nickname = resultUser.getNickname();
    this.isloginUserFollow = loginUser.hasFollowing(resultUser);
  }

  public static List<FollowDto> listFrom(User loginUser, List<User> resultUserList) {
    return resultUserList.stream()
                         .map(resultUser -> new FollowDto(loginUser, resultUser))
                         .collect(Collectors.toList());
  }
}

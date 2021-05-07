package com.sns.zuzuclub.controller.profile.dto;

import com.sns.zuzuclub.domain.user.model.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileResponseDto {

  @ApiModelProperty(value = "닉네임")
  private String nickname;

  @ApiModelProperty(value = "프로필 이미지 Url")
  private String profileImageUrl;

  @ApiModelProperty(value = "소개")
  private String introduction;

  @ApiModelProperty(value = "관심 종목 수")
  private int userStockScrapCount;

  @ApiModelProperty(value = "팔로워 수")
  private int followerCount;

  @ApiModelProperty(value = "팔로잉 수")
  private int followingCount;

  @ApiModelProperty(value = "작성글 수")
  private int postCount;

  @Builder
  public ProfileResponseDto(String nickname, String profileImageUrl, String introduction,
      int userStockScrapCount, int followerCount, int followingCount, int postCount) {
    this.nickname = nickname;
    this.profileImageUrl = profileImageUrl;
    this.introduction = introduction;
    this.userStockScrapCount = userStockScrapCount;
    this.followerCount = followerCount;
    this.followingCount = followingCount;
    this.postCount = postCount;
  }

  public static ProfileResponseDto fromEntity(UserInfo userInfo){
    return ProfileResponseDto.builder()
                             .nickname(userInfo.getNickname())
                             .profileImageUrl(userInfo.getProfileImageUrl())
                             .introduction(userInfo.getIntroduction())
                             .userStockScrapCount(userInfo.getUser().getUserStockScrapCount())
                             .followerCount(userInfo.getUser().getFollowerCount())
                             .followingCount(userInfo.getUser().getFollowingCount())
                             .postCount(userInfo.getUser().getPostCount())
                             .build();
  }
}

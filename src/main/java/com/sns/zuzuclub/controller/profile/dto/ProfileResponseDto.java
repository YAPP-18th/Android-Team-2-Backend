package com.sns.zuzuclub.controller.profile.dto;

import com.sns.zuzuclub.controller.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.user.model.User;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
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

  @ApiModelProperty(value = "작성한 게시글 리스트")
  private List<PostResponseDto>  postResponseDtoList;

  @ApiModelProperty(value = "작성한 게시글 리스트")
  private boolean isLoginUserProfile;

  public ProfileResponseDto(User profileUser, Long loginUserId) {

    this.isLoginUserProfile = profileUser.getId().equals(loginUserId);

    this.nickname = profileUser.getNickname();
    this.profileImageUrl = profileUser.getProfileImageUrl();
    this.introduction = profileUser.getIntroduction();
    this.userStockScrapCount = profileUser.getUserStockScrapCount();
    this.followerCount = profileUser.getFollowerCount();
    this.followingCount = profileUser.getFollowingCount();
    this.postCount = profileUser.getPostCount();

    this.postResponseDtoList = PostResponseDto.ListFrom(profileUser.getPostList(), loginUserId);
  }
}

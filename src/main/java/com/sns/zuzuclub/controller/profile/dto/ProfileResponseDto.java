package com.sns.zuzuclub.controller.profile.dto;

import com.sns.zuzuclub.controller.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
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

  @ApiModelProperty(value = "작성한 게시글 리스트")
  private List<PostResponseDto>  postResponseDtoList;


  public ProfileResponseDto(UserInfoRepository userInfoRepository, UserInfo userInfo) {
    this.nickname = userInfo.getNickname();
    this.profileImageUrl = userInfo.getProfileImageUrl();
    this.introduction = userInfo.getIntroduction();

    User user = userInfo.getUser();
    this.userStockScrapCount = user.getUserStockScrapCount();
    this.followerCount = user.getFollowerCount();
    this.followingCount = user.getFollowingCount();
    this.postCount = user.getPostCount();

    this.postResponseDtoList = PostResponseDto.toListFrom(userInfoRepository, user.getPostList(), userInfo.getId());
  }
}

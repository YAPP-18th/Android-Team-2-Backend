package com.sns.zuzuclub.controller.signup.dto;

import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class SignupRequestDto {

  @ApiModelProperty(value = "닉네임", example = "잼민이")
  private String nickname;

  @ApiModelProperty(value = "소개", example = "와타시잼민이")
  private String introduction;

  @ApiModelProperty(value = "관심 종목 id 리스트", example = "1, 2, 3, 4")
  private List<Long> likeStockIdList;

  public UserInfo createUserInfoEntity(User user){
    return UserInfo.builder()
                   .user(user)
                   .nickname(nickname)
                   .introduction(introduction)
                   .profileImageUrl(null)
                   .build();
  }
}

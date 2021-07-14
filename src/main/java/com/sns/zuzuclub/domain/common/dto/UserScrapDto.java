package com.sns.zuzuclub.domain.common.dto;

import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserScrapDto {

  private Long userId;
  private String nickname;
  private List<UserStockScrapDto> userStockScrapDtoList;

  public UserScrapDto(User user, List<UserStockScrap> userStockScrapList) {
    this.userId = user.getId();
    this.nickname = user.getNickname();
    this.userStockScrapDtoList = UserStockScrapDto.toListFrom(userStockScrapList);
  }
}

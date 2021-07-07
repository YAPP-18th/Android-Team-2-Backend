package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostReactionType;
import com.sns.zuzuclub.domain.post.model.PostReaction;
import com.sns.zuzuclub.domain.user.model.User;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ReactionUserDto {
  private Long userId;
  private String profileImageUrl;
  private String nickname;
  private PostReactionType postReactionType;

  public ReactionUserDto(PostReaction postReaction) {

    User user = postReaction.getUser();
    this.userId = user.getId();
    this.profileImageUrl = user.getProfileImageUrl();
    this.nickname = user.getNickname();

    this.postReactionType = postReaction.getReactionType();
  }
}

package com.sns.zuzuclub.domain.post.dto;


import com.sns.zuzuclub.domain.post.model.PostReactionType;
import com.sns.zuzuclub.domain.post.model.PostReaction;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreatePostReactionResponseDto {

  private Long userId;
  private Long postId;
  private PostReactionType postReactionType;

  public CreatePostReactionResponseDto(PostReaction postReaction) {
    this.userId = postReaction.getUser().getId();
    this.postId = postReaction.getPost().getId();
    this.postReactionType = postReaction.getReactionType();
  }
}

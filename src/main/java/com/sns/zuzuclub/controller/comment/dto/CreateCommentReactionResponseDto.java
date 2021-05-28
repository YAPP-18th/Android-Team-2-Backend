package com.sns.zuzuclub.controller.comment.dto;

import com.sns.zuzuclub.constant.CommentReactionType;
import com.sns.zuzuclub.domain.comment.model.CommentReaction;
import lombok.Getter;

@Getter
public class CreateCommentReactionResponseDto {

  private Long commentId;
  private CommentReactionType commentReactionType;

  public  CreateCommentReactionResponseDto(CommentReaction commentReaction) {
    this.commentId = commentReaction.getComment().getId();
    this.commentReactionType = commentReaction.getCommentReactionType();
  }
}

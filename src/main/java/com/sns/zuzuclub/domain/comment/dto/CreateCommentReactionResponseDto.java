package com.sns.zuzuclub.domain.comment.dto;

import com.sns.zuzuclub.domain.comment.model.CommentReactionType;
import com.sns.zuzuclub.domain.comment.model.CommentReaction;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateCommentReactionResponseDto {

  private Long commentId;
  private CommentReactionType commentReactionType;

  public  CreateCommentReactionResponseDto(CommentReaction commentReaction) {
    this.commentId = commentReaction.getComment().getId();
    this.commentReactionType = commentReaction.getCommentReactionType();
  }
}

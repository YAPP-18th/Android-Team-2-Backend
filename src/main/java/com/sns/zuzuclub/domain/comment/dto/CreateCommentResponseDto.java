package com.sns.zuzuclub.domain.comment.dto;

import com.sns.zuzuclub.domain.comment.model.Comment;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateCommentResponseDto {


  private Long commentId;
  private String content;
  private Long postId;
  private Long parentCommentId;

  public CreateCommentResponseDto(Comment comment) {
    this.commentId = comment.getId();
    this.content = comment.getContent();
    this.postId = comment.getPost().getId();
    this.parentCommentId = comment.getParentCommentId();
  }
}

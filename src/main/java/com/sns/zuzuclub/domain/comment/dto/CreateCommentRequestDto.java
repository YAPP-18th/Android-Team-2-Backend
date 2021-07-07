package com.sns.zuzuclub.domain.comment.dto;

import com.sns.zuzuclub.domain.comment.helper.CommentHelper;
import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.comment.repository.CommentRepository;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CreateCommentRequestDto {

  @ApiModelProperty(value = "댓글 내용", example = "")
  private String content;

  @ApiModelProperty(value = "부모 댓글 id", example = "")
  private Long parentCommentId;

  public Comment toEntity(CommentRepository commentRepository, User user, Post post){
    Comment parentComment = getParentComment(commentRepository);
    return Comment.builder()
                  .user(user)
                  .post(post)
                  .content(content)
                  .parentComment(parentComment)
                  .build();
  }

  private Comment getParentComment(CommentRepository commentRepository) {
    Comment parentComment = null;
    if (parentCommentId == -1){
      return parentComment;
    }
    return CommentHelper.findCommentById(commentRepository, parentCommentId);
  }
}

package com.sns.zuzuclub.domain.comment.helper;

import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.comment.repository.CommentRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.CommentErrorCodeType;

public class CommentHelper {

  public static Comment findCommentById(CommentRepository commentRepository, Long commentId) {
    return commentRepository.findById(commentId)
                            .orElseThrow(() -> new CustomException(CommentErrorCodeType.INVALID_COMMENT_ID));
  }

}

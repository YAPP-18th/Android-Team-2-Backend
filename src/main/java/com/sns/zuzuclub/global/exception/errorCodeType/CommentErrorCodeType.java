package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum CommentErrorCodeType {

  INVALID_COMMENT_ID(451, "유효하지 않은 댓글 id"),
  INVALID_COMMENT_REACTION(452, "유효하지 않은 댓글 반응");

  private final int errorCode;
  private final String message;

  CommentErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

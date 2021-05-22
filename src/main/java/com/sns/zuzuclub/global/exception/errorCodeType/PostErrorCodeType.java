package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum PostErrorCodeType {

  INVALID_POST_ID(441, "유효하지 않은 게시글 id"),
  INVALID_FEED_TYPE(442, "유효하지 피드 타입"),
  INVALID_POST_REACTION(443, "유효하지 않은 게시글 반응");

  private final int errorCode;
  private final String message;

  PostErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

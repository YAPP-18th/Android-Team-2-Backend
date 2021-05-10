package com.sns.zuzuclub.constant;

public enum FeedType {
  ALL("최신순 정렬"),
  HOT("반응이 많은 순서로 정렬"),
  FRIENDS("친구게시물을 순서대로 정렬")
  ;

  private final String description;

  FeedType(String description) {
    this.description = description;
  }
}

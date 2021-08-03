package com.sns.zuzuclub.domain.notification.model;

import lombok.Getter;

@Getter
public enum NotificationType {
  FOLLOW("팔로우 알림") {
    @Override
    public String getRedirectUrl(Long targetId) {
      return "zuzuclub.site/profile/" + targetId;
    }
  },
  COMMENT("게시글 댓글 알림") {
    @Override
    public String getRedirectUrl(Long targetId) {
      return "zuzuclub.site/posts/" + targetId;
    }
  },
  POST_REACTION("게시글 반응 알림") {
    @Override
    public String getRedirectUrl(Long targetId) {
      return "zuzuclub.site/posts/" + targetId;
    }
  },
  COMMENT_REACTION("댓글 반응 알림") {
    @Override
    public String getRedirectUrl(Long targetId) {
      return "zuzuclub.site/posts/" + targetId;
    }
  };

  private String title;

  NotificationType(String title) {
    this.title = title;
  }

  public abstract String getRedirectUrl(Long targetId);
}

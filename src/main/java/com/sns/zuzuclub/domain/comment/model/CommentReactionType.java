package com.sns.zuzuclub.domain.comment.model;

import lombok.Getter;

@Getter
public enum CommentReactionType {

  SYMPATHY("공감해요"),
  FUNNY("좋아요"),
  ANGRY("화나요"),
  SAD("슬퍼요"),
  FIGHTING("힘내요"),
  ENVIOUS("부러워요"),
  THINK("절레절레");

  private final String content;

  CommentReactionType(String content) {
    this.content = content;
  }
}

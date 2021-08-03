package com.sns.zuzuclub.domain.post.model;

import lombok.Getter;

@Getter
public enum PostReactionType {

  SYMPATHY("공감해요"),
  FUNNY("웃겨요"),
  ANGRY("화나요"),
  SAD("슬퍼요"),
  FIGHTING("힘내요"),
  ENVIOUS("부러워요"),
  THINK("절레절레");


  private final String content;

  PostReactionType(String content) {
    this.content = content;
  }

}

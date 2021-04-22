package com.sns.zuzuclub.constant;

public enum PostEmotionType {

  UP(1),
  DOWN(2),
  EXPECT(3),
  UNSTABLE(4);

  private final int value;

  private PostEmotionType(int value) {
    this.value = value;
  }
}

package com.sns.zuzuclub.constant;

public enum PostEmotion {

    UP(1), DOWN(2), EXPECT(3), UNSTABLE(4);

    private final int value;
    
    private PostEmotion(int value) {
        this.value = value;
    }
}

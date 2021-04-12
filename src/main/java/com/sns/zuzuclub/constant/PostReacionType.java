package com.sns.zuzuclub.constant;

public enum PostReacionType {
    
    SYMPATHY(1), FUNNY(2), ANGRY(3), SAD(4), FIGHTING(5), ENVIOUS(6), THINK(7);

    private final int value;

    private PostReacionType(int value) {
        this.value = value;
    }
    
}

package com.sns.zuzuclub.domain.post.model;

import java.util.EnumMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum PostEmotionType {

  UP("SUNNY"),
  DOWN("RAINY"),
  EXPECT("WINDY"),
  UNSTABLE("CLOUDY");

  private final String weather;

  PostEmotionType(String weather) {
    this.weather = weather;
  }

  public static Map<PostEmotionType, Integer> initPostEmotionTypeWithCountMap(){
    Map<PostEmotionType, Integer> postEmotionTypeIntegerMap = new EnumMap<>(PostEmotionType.class);
    for (PostEmotionType postEmotionType : PostEmotionType.values()) {
      postEmotionTypeIntegerMap.put(postEmotionType, 0);
    }
    return postEmotionTypeIntegerMap;
  }
}

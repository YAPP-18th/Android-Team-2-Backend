package com.sns.zuzuclub.controller.home.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import java.util.Map.Entry;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WeatherDto {

  private String weather;
  private float ratio;

  public WeatherDto(Entry<PostEmotionType, Integer> weather, int postCount) {
    this.weather = weather.getKey().toString();
    this.ratio = (float)weather.getValue() / postCount;
  }
}

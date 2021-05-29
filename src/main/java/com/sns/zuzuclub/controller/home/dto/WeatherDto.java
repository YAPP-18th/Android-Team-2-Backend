package com.sns.zuzuclub.controller.home.dto;

import com.sns.zuzuclub.domain.homeInfo.model.Weather;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WeatherDto {

  private String weather;
  private float percentage;

  public WeatherDto(Weather weather) {
    this.weather = weather.getPostEmotionType()
                          .getWeather();
    this.percentage = weather.getPercentage();
  }
}

package com.sns.zuzuclub.controller.home.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class HomeResponseDto {

  private WeatherDto weatherDto;
  private List<HotStockDto> hotStockDtoList;
  private List<UserStockScrapDto> userStockScrapDtoList;

  @Builder
  public HomeResponseDto(Entry<PostEmotionType, Integer> weather,
                         int postCount,
                         Map<PostEmotionType, Stock> hotStockMap,
                         List<UserStockScrap> userStockScrapList) {
    this.weatherDto = new WeatherDto(weather, postCount);
    this.hotStockDtoList = HotStockDto.toListFrom(hotStockMap);
    this.userStockScrapDtoList = UserStockScrapDto.toListFrom(userStockScrapList);
  }
}

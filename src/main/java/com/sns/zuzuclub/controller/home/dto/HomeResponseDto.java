package com.sns.zuzuclub.controller.home.dto;

import com.sns.zuzuclub.domain.homeInfo.model.HotStock;
import com.sns.zuzuclub.domain.homeInfo.model.Weather;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import java.util.List;
import lombok.Getter;

@Getter
public class HomeResponseDto {

  private WeatherDto weatherDto;
  private List<HotStockDto> hotStockDtoList;
  private List<UserStockScrapDto> userStockScrapDtoList;

  public HomeResponseDto(Weather weather, List<HotStock> hotStockList, List<UserStockScrap> userStockScrapList) {
    this.weatherDto = new WeatherDto(weather);
    this.hotStockDtoList = HotStockDto.toListFrom(hotStockList);
    this.userStockScrapDtoList = UserStockScrapDto.toListFrom(userStockScrapList);
  }
}

package com.sns.zuzuclub.domain.homeInfo.application;

import com.sns.zuzuclub.controller.home.dto.HomeResponseDto;
import com.sns.zuzuclub.domain.homeInfo.model.HotStock;
import com.sns.zuzuclub.domain.homeInfo.model.Weather;
import com.sns.zuzuclub.domain.homeInfo.repository.HotStockRepository;
import com.sns.zuzuclub.domain.homeInfo.repository.WeatherRepository;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import com.sns.zuzuclub.domain.user.repository.UserStockScrapRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.HomeErrorCodeType;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeService {

  private final WeatherRepository weatherRepository;
  private final HotStockRepository hotStockRepository;
  private final UserStockScrapRepository userStockScrapRepository;

  public HomeResponseDto getHomeInfo(Long userId) {
    Weather weather = weatherRepository.findTopByOrderByIdDesc()
                                       .orElseThrow(() -> new CustomException(HomeErrorCodeType.EMPTY_WEATHER_DATA));
    List<HotStock> hotStockList = hotStockRepository.findAllByRankingIs(1);
    List<UserStockScrap> userStockScrapList = userStockScrapRepository.findTop6ByUser_Id(userId);
    return new HomeResponseDto(weather, hotStockList, userStockScrapList);
  }
}

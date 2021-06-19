package com.sns.zuzuclub.domain.homeInfo.application;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.controller.home.dto.HomeResponseDto;
import com.sns.zuzuclub.controller.home.dto.HotStockDto;
import com.sns.zuzuclub.controller.home.dto.UserStockScrapDto;
import com.sns.zuzuclub.domain.homeInfo.model.HotStock;
import com.sns.zuzuclub.domain.homeInfo.model.Weather;
import com.sns.zuzuclub.domain.homeInfo.repository.HotStockRepository;
import com.sns.zuzuclub.domain.homeInfo.repository.WeatherRepository;
import com.sns.zuzuclub.domain.homeInfo.service.HotStockService;
import com.sns.zuzuclub.domain.homeInfo.service.WeatherService;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.PostedStockRepository;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import com.sns.zuzuclub.domain.user.repository.UserStockScrapRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.HomeErrorCodeType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class HomeInfoService {

  private final WeatherService weatherService;
  private final HotStockService hotStockService;

  private final PostRepository postRepository;
  private final PostedStockRepository postedStockRepository;
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

  public List<HotStockDto> getHotStockRanking(PostEmotionType postEmotionType) {
    List<HotStock> hotStockList = hotStockRepository.findAllByPostEmotionType(postEmotionType);
    return HotStockDto.toListFrom(hotStockList);
  }

  public List<UserStockScrapDto> getUserStockScrap(Long userId) {
    List<UserStockScrap> userStockScrapList = userStockScrapRepository.findAllByUser_Id(userId);
    return UserStockScrapDto.toListFrom(userStockScrapList);
  }

  @Transactional
  public void scheduling(LocalDateTime from){
    // 1.
    List<Post> postList = postRepository.findAllByCreatedAtAfter(from);
    if (postList.isEmpty()) {
      return;
    }
    weatherService.createWeather(postList);

    // 2.
    List<PostedStock> postedStockList = postedStockRepository.findAllByCreatedAtAfter(from);
    if (postedStockList.isEmpty()) {
      return;
    }
    hotStockService.updateHotStockRank(postedStockList);

  }
}

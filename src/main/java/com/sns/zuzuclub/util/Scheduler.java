package com.sns.zuzuclub.util;

import com.sns.zuzuclub.domain.homeInfo.service.HotStockService;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.homeInfo.service.WeatherService;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.repository.PostedStockRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Scheduler {

  private final WeatherService homeWeatherService;
  private final HotStockService hotStockService;

  private final PostRepository postRepository;
  private final PostedStockRepository postedStockRepository;

  // 초, 분, 시, 일, 월, 주 순서
  @Scheduled(cron = "0 0 09 * * ?")
  public void calculateHomeInfo(){

    // ToDo: 스케줄러 실행 로그 찍기
    // from 이후로 작성된 모든 포스트를 가져온다.
    LocalDateTime from = LocalDateTime.now().minusDays(1); // 1일 전

    // 1.
    List<Post> postList = postRepository.findAllByCreatedAtAfter(from);
    if (postList.isEmpty()) {
      return;
    }
    homeWeatherService.createWeather(postList);

    // 2.
    List<PostedStock> postedStockList = postedStockRepository.findAllByCreatedAtAfter(from);
    if (postedStockList.isEmpty()) {
      return;
    }
    hotStockService.updateHotStockRank(postedStockList);
  }
}

package com.sns.zuzuclub.util;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.homeInfo.model.Weather;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.homeInfo.application.HomeWeatherService;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Scheduler {

  private final HomeWeatherService homeWeatherService;

  // 초, 분, 시, 일, 월, 주 순서
  @Scheduled(cron = "0 0 09 * * ?")
  public void calculateHomeInfo(){

    // ToDo: 스케줄러 실행 로그 찍기
    LocalDateTime from = LocalDateTime.now().minusDays(1); // 1일 전
    homeWeatherService.createWeather(from);

  }
}

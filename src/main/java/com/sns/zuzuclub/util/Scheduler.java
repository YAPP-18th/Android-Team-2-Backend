package com.sns.zuzuclub.util;

import com.sns.zuzuclub.domain.homeInfo.application.HomeInfoService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Scheduler {

  private final HomeInfoService homeInfoService;

  // 초, 분, 시, 일, 월, 주 순서
  @Scheduled(cron = "0 0 09 * * ?")
  public void calculateHomeInfo() {
    // ToDo: 스케줄러 실행 로그 찍기
    // from 이후로 작성된 모든 포스트를 가져온다.
    LocalDateTime from = LocalDateTime.now().minusDays(1); // 1일 전
    homeInfoService.scheduling(from);
  }
}

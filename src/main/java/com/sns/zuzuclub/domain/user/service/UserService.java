package com.sns.zuzuclub.domain.user.service;

import com.sns.zuzuclub.domain.user.model.Report;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.ReportRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;
  private final ReportRepository reportRepository;

  public void validateNickname(Long loginUserId, String nickname){
    Optional<User> targetUser = userRepository.findByNickname(nickname);
    targetUser.ifPresent(user -> {
      boolean isSameUser = user.getId().equals(loginUserId);
      if (!isSameUser) {
        throw new CustomException(UserErrorCodeType.DUPLICATE_NICKNAME);
      }
    });
  }

  public void validateSuspension(Long userId){

    List<Report> reportList= reportRepository.findAllByTargetUserIdOrderByCreatedAtDesc(userId);
    int reportCount = reportList.size();

    if (reportCount < 5){
      return;
    }

    LocalDateTime lastReportDate = reportList.get(0).getCreatedAt();
    Duration duration = Duration.between(lastReportDate, LocalDateTime.now());
    long days = duration.toDays();

    if(reportCount >= 10 && days <= 30){
      throw new CustomException(UserErrorCodeType.ONE_MONTH_SUSPENDED_USER);
    } else if (days <= 14) {
      throw new CustomException(UserErrorCodeType.TWO_WEEK_SUSPENDED_USER);
    }

  }

}

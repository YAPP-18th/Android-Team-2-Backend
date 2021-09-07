package com.sns.zuzuclub.domain.user.service;

import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.Report;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.ReportRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.SuspendException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import java.time.Duration;
import java.time.LocalDateTime;
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

  public void validateSuspension(Long userId) {

    User user = UserHelper.findUserById(userRepository, userId);
    int reportCount = user.getReportCount();

    if (reportCount < 5){
      return;
    }

    Report recentReport = reportRepository.findTop1ByTargetUserIdOrderByCreatedAtDesc(userId);

    if (isSuspended(reportCount, recentReport)){
      throw new SuspendException(UserErrorCodeType.SUSPENDED_USER,
                                 user.getNickname(),
                                 user.getReportCount(),
                                 recentReport.getCreatedAt());
    }
  }

  private boolean isSuspended(int reportCount, Report recentReport) {

    LocalDateTime lastReportDate = recentReport.getCreatedAt();
    long days = Duration.between(lastReportDate, LocalDateTime.now()).toDays();

    return (reportCount < 10 && days <= 14) || (reportCount >= 10 && days <= 30);
  }

}

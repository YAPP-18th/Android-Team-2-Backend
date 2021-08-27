package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.domain.user.model.Report;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.ReportRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {

  private final UserRepository userRepository;
  private final ReportRepository reportRepository;

  @Transactional
  public void report(Long userId, String targetNickname) {

    User targetUser = userRepository.findByNickname(targetNickname)
                                    .orElseThrow(() -> new CustomException(UserErrorCodeType.INVALID_USER));
    Report report = new Report(userId, targetUser);
    reportRepository.save(report);
  }
}

package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.domain.user.model.Report;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.ReportRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportService {

  private final JavaMailSender javaMailSender;
  private final UserRepository userRepository;
  private final ReportRepository reportRepository;

  @Transactional
  public void report(Long userId, String targetNickname) {

    User targetUser = userRepository.findByNickname(targetNickname)
                                    .orElseThrow(() -> new CustomException(UserErrorCodeType.INVALID_USER));
    Report report = new Report(userId, targetUser);
    reportRepository.save(report);
    log.info("메일 전송 시작");
    sendSuspensionEmail(targetUser);
  }

  private void sendSuspensionEmail(User targetUser) {
    int reportCount = targetUser.getReportCount();
    if(reportCount > 5){
      SimpleMailMessage message = new SimpleMailMessage();
      message.setTo("zuzuclub.official@gmail.com");
      message.setFrom("zuzuclub.official@gmail.com");
      message.setSubject("[신고 누적 알림] - " + targetUser.getNickname());
      message.setText(targetUser.getNickname() + "의 누적횟수 : " + reportCount);
      javaMailSender.send(message);
      log.info("메일 전송 완료");
    }
  }
}

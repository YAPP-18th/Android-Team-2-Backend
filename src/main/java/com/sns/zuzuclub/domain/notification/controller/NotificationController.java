package com.sns.zuzuclub.domain.notification.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.domain.notification.application.NotificationService;
import com.sns.zuzuclub.domain.notification.dto.FcmTokenDto;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NotificationController {

  private final JwtTokenProvider jwtTokenProvider;
  private final NotificationService notificationService;

  @ApiOperation(
      value = "FCM TOKEN 수신",
      notes = "<h3>\n"
          + "- 안드로이드 기기의 FCM 토큰을 받습니다.\n"
          + "\n"
          + "</h3>"
  )
  @PostMapping("/fcm")
  public CommonResult getFcmToken(@RequestHeader(value = "Authorization") String jwtToken, @RequestBody FcmTokenDto fcmTokenDto)
  {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    notificationService.getFcmToken(userId, fcmTokenDto.getFcmToken());
    log.info("FCM TOKEN 수신");
    return ResponseForm.getSuccessResult("FCM TOKEN 수신");
  }
}

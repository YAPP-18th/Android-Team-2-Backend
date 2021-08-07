package com.sns.zuzuclub.domain.notification.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.domain.notification.application.NotificationService;
import com.sns.zuzuclub.domain.notification.dto.PushNotificationDto;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.MultipleResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class NotificationController {

  private final JwtTokenProvider jwtTokenProvider;
  private final NotificationService notificationService;

  @ApiOperation(
      value = "알림 목록 조회",
      notes = "<h3>\n"
          + "- 자신의 알림 목록을 조회합니다.\n"
          + "- 읽은 알림과 읽지 않은 알림으로 구분됩니다.\n"
          + "- 알림창에 한 번 들어오면, 모두 읽음 처리가 됩니다.\n"
          + "\n"
          + "</h3>"
  )
  @GetMapping("/notifications")
  public MultipleResult<PushNotificationDto> getNotifications(@RequestHeader(value = "Authorization") String jwtToken)
  {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    List<PushNotificationDto> pushNotificationDtoList = notificationService.getNotifications(userId);
    log.info(pushNotificationDtoList.toString());
    return ResponseForm.getMultipleResult(pushNotificationDtoList, "알림 목록 조회");
  }

}

package com.sns.zuzuclub.domain.notification.dto;

import com.sns.zuzuclub.domain.notification.model.PushNotification;
import com.sns.zuzuclub.global.util.TimeConvertor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PushNotificationDto {

  private String profileImageUrl;
  private String message;
  private String createdAt;
  private String redirectUrl;
  private boolean isRead;

  public PushNotificationDto(String profileImageUrl, PushNotification pushNotification) {
    this.profileImageUrl = profileImageUrl;
    this.message = pushNotification.getAlarmMessage();
    this.redirectUrl = pushNotification.getRedirectUrl();
    this.createdAt = TimeConvertor.convertToString(pushNotification.getCreatedAt());
    this.isRead = pushNotification.isRead();
  }
}

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
  private Long redirectId;
  private String notificationType;
  private Long reactionType;
  private boolean isRead;

  public PushNotificationDto(String profileImageUrl, PushNotification pushNotification) {
    this.profileImageUrl = profileImageUrl;
    this.message = pushNotification.getAlarmMessage();
    this.redirectId = pushNotification.getRedirectTargetId();
    this.notificationType = pushNotification.getNotificationType().toString();
    this.createdAt = TimeConvertor.convertToString(pushNotification.getCreatedAt());
    this.isRead = pushNotification.isRead();
    this.reactionType = pushNotification.parseReactionType();
  }
}

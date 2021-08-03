package com.sns.zuzuclub.domain.notification.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class NotificationDto {

  private String title;
  private String body;
  private Map<String, String> data = new HashMap<>();
  private String fcmToken;

  @Builder
  public NotificationDto(String title,
                         String body,
                         String fcmToken,
                         Map<String, String> data) {
    this.title = title;
    this.body = body;
    this.fcmToken = fcmToken;
    this.data.putAll(data);
  }
}

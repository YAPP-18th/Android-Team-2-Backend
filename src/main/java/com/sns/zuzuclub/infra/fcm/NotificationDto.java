package com.sns.zuzuclub.infra.fcm;

import com.google.firebase.messaging.Notification;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class NotificationDto {

  private String title;
  private String body;
  private Map<String, String> data = new HashMap<>();
  private String fcmToken;

  public Notification getNotification() {
    return Notification.builder()
                       .setTitle(title)
                       .setBody(body)
                       .build();
  }
}

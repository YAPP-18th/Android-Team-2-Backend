package com.sns.zuzuclub.domain.notification.model;

import com.sns.zuzuclub.domain.common.model.AuditEntity;
import com.sns.zuzuclub.domain.notification.dto.FcmNotificationDto;
import com.sns.zuzuclub.domain.user.model.User;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PushNotification extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private Long senderId;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  private Long redirectTargetId;

  private String alarmMessage;

  private boolean isRead;

  @Builder
  public PushNotification(Long userId,
                          Long senderId,
                          NotificationType notificationType,
                          Long redirectTargetId,
                          String alarmMessage) {
    this.userId = userId;
    this.senderId = senderId;
    this.notificationType = notificationType;
    this.redirectTargetId = redirectTargetId;
    this.alarmMessage = alarmMessage;
    this.isRead = false;
  }

  public FcmNotificationDto createNotificationDto(User targetUser){
    Map<String, String> data = new HashMap<>();
    data.put("redirectUrl", notificationType.getRedirectUrl(redirectTargetId));
    return FcmNotificationDto.builder()
                             .title(notificationType.getTitle()) // "팔로우 알림"
                             .body(alarmMessage) //""
                             .fcmToken(targetUser.getFcmToken())
                             .data(data)
                             .build();
  }

  public void updateReadStatusTrue(){
    this.isRead = true;
  }

  public String getRedirectUrl(){
    return this.notificationType.getRedirectUrl(redirectTargetId);
  }
}

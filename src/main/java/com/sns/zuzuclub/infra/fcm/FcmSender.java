package com.sns.zuzuclub.infra.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.sns.zuzuclub.domain.notification.dto.FcmNotificationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FcmSender {

  public void sendMessage(FcmNotificationDto fcmNotificationDto) {
    Message message = getMessage(fcmNotificationDto);
    try {
      String messageId = FirebaseMessaging.getInstance().send(message);
      log.info("Send Message Id : {}", messageId);
    } catch (FirebaseMessagingException e) {
      log.error(e.getMessage());
    }
  }

  private Message getMessage(FcmNotificationDto fcmNotificationDto) {
    return Message.builder()
                  .setNotification(getNotification(fcmNotificationDto))
                  .putAllData(fcmNotificationDto.getData())
                  .setToken(fcmNotificationDto.getFcmToken())
                  .build();
  }

  private Notification getNotification(FcmNotificationDto fcmNotificationDto) {
    return Notification.builder()
                       .setTitle(fcmNotificationDto.getTitle())
                       .setBody(fcmNotificationDto.getBody())
                       .build();
  }
}

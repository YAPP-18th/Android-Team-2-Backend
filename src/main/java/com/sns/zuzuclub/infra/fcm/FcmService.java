package com.sns.zuzuclub.infra.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FcmService {

  public void sendMessage(NotificationDto notificationDto) {
    Message message = getMessage(notificationDto);
    try {
      String messageId = FirebaseMessaging.getInstance().send(message);
      log.info("Send Message Id : {}", messageId);
    } catch (FirebaseMessagingException e) {
      log.error(e.getMessage());
    }
  }

  private Message getMessage(NotificationDto notificationDto) {
    return Message.builder()
                  .setNotification(notificationDto.getNotification())
                  .putAllData(notificationDto.getData())
                  .setToken(notificationDto.getFcmToken())
                  .build();
  }
}

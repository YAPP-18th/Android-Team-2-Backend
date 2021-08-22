package com.sns.zuzuclub.domain.notification.application;


import com.sns.zuzuclub.domain.notification.dto.PushNotificationDto;
import com.sns.zuzuclub.domain.notification.model.PushNotification;
import com.sns.zuzuclub.domain.notification.repository.PushNotificationRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NotificationService {

  private final PushNotificationRepository pushNotificationRepository;
  private final UserRepository userRepository;

  @Transactional
  public List<PushNotificationDto> getNotifications(Long userId) {
    List<PushNotification> pushNotificationList = pushNotificationRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    List<PushNotificationDto> pushNotificationDtoList = pushNotificationList.stream()
                                                            .map(this::getPushNotificationDto)
                                                            .collect(Collectors.toList());
    pushNotificationList.forEach(PushNotification::updateReadStatusTrue);
    return pushNotificationDtoList;
  }

  private PushNotificationDto getPushNotificationDto(PushNotification pushNotification) {
    User sender = UserHelper.findUserById(userRepository, pushNotification.getSenderId());
    return new PushNotificationDto(sender.getProfileImageUrl(), pushNotification);
  }
}

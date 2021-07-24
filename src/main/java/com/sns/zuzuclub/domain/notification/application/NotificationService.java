package com.sns.zuzuclub.domain.notification.application;

import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NotificationService {

  private final UserRepository userRepository;

  @Transactional
  public void getFcmToken(Long userId, String fcmToken) {
    User user = UserHelper.findUserById(userRepository, userId);
    user.updateFcmToken(fcmToken);
  }
}

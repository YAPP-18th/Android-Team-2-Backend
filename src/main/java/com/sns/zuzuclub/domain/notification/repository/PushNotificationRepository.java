package com.sns.zuzuclub.domain.notification.repository;

import com.sns.zuzuclub.domain.notification.model.PushNotification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushNotificationRepository extends JpaRepository<PushNotification, Long> {
  List<PushNotification> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}

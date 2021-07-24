package com.sns.zuzuclub.domain.notification.repository;

import com.sns.zuzuclub.domain.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}

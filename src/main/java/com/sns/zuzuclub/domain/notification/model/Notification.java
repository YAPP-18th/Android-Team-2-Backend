package com.sns.zuzuclub.domain.notification.model;

import com.sns.zuzuclub.domain.common.model.AuditEntity;
import com.sns.zuzuclub.domain.user.model.User;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Notification extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  private Long redirectTargetId;

  private String alarmMessage;

  private boolean isRead;
}

package com.sns.zuzuclub.domain.alarm;

import com.sns.zuzuclub.constant.NotificationType;
import com.sns.zuzuclub.domain.user.UserHistory;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private UserHistory userHistory;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  private Long targetId;

  private String alarmMessage;

  private boolean isRead;
}

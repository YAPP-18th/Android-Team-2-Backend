package com.sns.zuzuclub.domain.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sns.zuzuclub.domain.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class UserFollow extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

/*
  - fromUser 가 toUser를 차단함
   */

  @ManyToOne(fetch = FetchType.LAZY)
  private User fromUser;

  @ManyToOne(fetch = FetchType.LAZY)
  private User toUser;
}

package com.sns.zuzuclub.domain.user.model;

import com.sns.zuzuclub.global.AuditEntity;
import javax.persistence.Entity;
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
public class UserBlock extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /*
  - fromUser 가 toUser 를 차단함
   */

  @ManyToOne(fetch = FetchType.LAZY)
  private User fromUser;

  @ManyToOne(fetch = FetchType.LAZY)
  private User toUser;
}

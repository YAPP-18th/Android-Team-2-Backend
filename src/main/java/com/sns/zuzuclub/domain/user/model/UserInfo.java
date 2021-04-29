package com.sns.zuzuclub.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.sns.zuzuclub.global.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserInfo extends AuditEntity {

  @Id
  private Long id;

  @Column(nullable = false, length = 20)
  private String nickname;

  @Column(length = 360)
  private String introduction;

  private String profileImageUrl; // 기본 값을 여기다가 초기화 해주면 좋을 것 같은데 흠

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private User user;


}

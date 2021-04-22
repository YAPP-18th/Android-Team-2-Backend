package com.sns.zuzuclub.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sns.zuzuclub.domain.AuditEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20)
  private String nickname;

  @Column(length = 360)
  private String introduction;

  private String profileImageUrl; // 기본 값을 여기다가 초기화 해주면 좋을 것 같은데 흠

  @OneToOne
  private UserHistory userHistory;

  @OneToOne
  private UserSecurity userSecurity;
}

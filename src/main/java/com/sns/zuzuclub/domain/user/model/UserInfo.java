package com.sns.zuzuclub.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.sns.zuzuclub.global.AuditEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserInfo extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  @MapsId
  private User user;

  @Column(nullable = false, length = 20)
  private String nickname;

  @Column(length = 360)
  private String introduction;

  private String profileImageUrl; // 기본 값을 여기다가 초기화 해주면 좋을 것 같은데 흠

  @Builder
  public UserInfo(User user, String nickname, String introduction, String profileImageUrl) {
    this.user = user;
    this.nickname = nickname;
    this.introduction = introduction;
    this.profileImageUrl = profileImageUrl;
  }
}

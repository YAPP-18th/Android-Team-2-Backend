package com.sns.zuzuclub.domain.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.sns.zuzuclub.constant.SocialProvider;
import com.sns.zuzuclub.constant.UserRole;

import com.sns.zuzuclub.global.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserSecurity extends AuditEntity {

  @Id
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @MapsId
  private User user;

  @Column(nullable = false)
  private String socialId;

  @Column(nullable = false)
  private SocialProvider socialProvider;

  @Column(nullable = false)
  private String jwtRefreshToken;

  @Column(nullable = false)
  private UserRole userRole;
}

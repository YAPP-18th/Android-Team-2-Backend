package com.sns.zuzuclub.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.sns.zuzuclub.constant.SocialProvider;
import com.sns.zuzuclub.constant.UserRole;

import com.sns.zuzuclub.domain.AuditEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserSecurity extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "userSecurity")
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

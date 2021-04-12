package com.sns.zuzuclub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sns.zuzuclub.constant.SocialProvicer;
import com.sns.zuzuclub.constant.UserRole;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "user_security")
@Getter
public class UserSecurity extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_security_id")
    private Long userSecurityId;

    @OneToOne
    private User user;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Column(name = "social_provider", nullable = false)
    private SocialProvicer socialProvider;

    @Column(name = "jwt_refresh_token", nullable = false)
    private String jwtRefreshToken;

    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    @Builder
    public UserSecurity(Long userSecurityId, User user, String socialId, SocialProvicer socialProvider,
        String jwtRefreshToken, UserRole userRole) {
        this.userSecurityId = userSecurityId;
        this.user = user;
        this.socialId = socialId;
        this.socialProvider = socialProvider;
        this.jwtRefreshToken = jwtRefreshToken;
        this.userRole = userRole;
    }
}

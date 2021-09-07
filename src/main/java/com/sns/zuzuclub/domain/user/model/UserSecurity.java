package com.sns.zuzuclub.domain.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.JwtErrorCodeType;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.sns.zuzuclub.config.security.social.SocialTokenProviderType;

import com.sns.zuzuclub.domain.common.model.AuditEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@NoArgsConstructor
@Entity
public class UserSecurity extends AuditEntity implements UserDetails {

  @Id
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(nullable = false)
  @MapsId
  private User user;

  @Column(nullable = false)
  private String socialId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private SocialTokenProviderType socialTokenProviderType;

  @Column(nullable = false)
  private String jwtRefreshToken;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRoleType userRoleType;

  @Builder
  public UserSecurity(User user, String socialId,
      SocialTokenProviderType socialTokenProviderType, String jwtRefreshToken,
      UserRoleType userRoleType) {
    this.user = user;
    this.socialId = socialId;
    this.socialTokenProviderType = socialTokenProviderType;
    this.jwtRefreshToken = jwtRefreshToken;
    this.userRoleType = userRoleType;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(this.userRoleType.getRole());
    ArrayList<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
    simpleGrantedAuthorities.add(simpleGrantedAuthority);
    return simpleGrantedAuthorities;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public String getUsername() {
    return this.id.toString();
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  public String getPassword() {
    return null;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  @Override
  public boolean isEnabled() {
    return true;
  }

  public void updateJwtRefreshToken(String reissuedJwtRefreshToken){
    this.jwtRefreshToken = reissuedJwtRefreshToken;
  }
}
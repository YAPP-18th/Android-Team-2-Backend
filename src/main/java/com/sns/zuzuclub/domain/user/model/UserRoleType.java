package com.sns.zuzuclub.domain.user.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRoleType {

  USER("ROLE_USER", "사용자"),
  ADMIN("ROLE_ADMIN", "관리자");

  private final String role;
  private final String description;
}

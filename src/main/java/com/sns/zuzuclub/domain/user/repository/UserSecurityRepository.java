package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.config.security.social.SocialTokenProviderType;
import com.sns.zuzuclub.domain.user.model.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {
  UserSecurity findBySocialTokenProviderTypeAndSocialId(SocialTokenProviderType socialTokenProviderType, String socialId);
}

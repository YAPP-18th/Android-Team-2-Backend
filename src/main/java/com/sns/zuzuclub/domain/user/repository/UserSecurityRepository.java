package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.constant.SocialTokenProviderType;
import com.sns.zuzuclub.domain.user.model.UserSecurity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSecurityRepository extends JpaRepository<UserSecurity, Long> {
  UserSecurity findBySocialTokenProviderTypeAndSocialId(SocialTokenProviderType socialTokenProviderType, String socialId);
  Optional<UserSecurity> findByUserId(Long userId);
}

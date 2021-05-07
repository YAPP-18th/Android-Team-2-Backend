package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.user.model.UserInfo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {

  Optional<UserInfo> findByNickname(String nickname);

}

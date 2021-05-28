package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.user.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  List<User> findAllByNicknameStartingWith(String keyword);
  boolean existsByNickname(String nickname);
}

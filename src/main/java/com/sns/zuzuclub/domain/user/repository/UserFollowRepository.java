package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
  boolean existsAllByFromUserAndToUser(User fromUser, User toUser);
}

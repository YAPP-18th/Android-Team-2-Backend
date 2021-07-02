package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserFollow;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {
  Optional<UserFollow> findByFromUser_IdAndToUser_Id(Long fromUserId, Long toUserId);
  boolean existsAllByFromUserAndToUser(User fromUser, User toUser);
}

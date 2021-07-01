package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserFollow;
import com.sns.zuzuclub.domain.user.repository.UserFollowRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

  private final UserRepository userRepository;
  private final UserFollowRepository userFollowRepository;

  @Transactional
  public void follow(Long userId, Long targetUserId) {
    User loginUser = UserHelper.findUserById(userRepository, userId);
    User targetUser = UserHelper.findUserById(userRepository, targetUserId);
    if(!userFollowRepository.existsAllByFromUserAndToUser(loginUser, targetUser)) {
      UserFollow userFollow = new UserFollow(loginUser, targetUser);
      userFollowRepository.save(userFollow);
    }
  }
}

package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.domain.notification.dto.NotificationDto;
import com.sns.zuzuclub.domain.notification.model.PushNotification;
import com.sns.zuzuclub.domain.notification.repository.PushNotificationRepository;
import com.sns.zuzuclub.domain.user.dto.follow.FollowDto;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserFollow;
import com.sns.zuzuclub.domain.user.repository.UserFollowRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import com.sns.zuzuclub.infra.fcm.FcmService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FollowService {

  private final FcmService fcmService;

  private final UserRepository userRepository;
  private final UserFollowRepository userFollowRepository;
  private final PushNotificationRepository pushNotificationRepository;

  @Transactional
  public void follow(Long userId, Long targetUserId) {
    User loginUser = UserHelper.findUserById(userRepository, userId);
    User targetUser = UserHelper.findUserById(userRepository, targetUserId);

    boolean exist = userFollowRepository.existsAllByFromUserAndToUser(loginUser, targetUser);
    if(exist) {
      return;
    }

    UserFollow userFollow = new UserFollow(loginUser, targetUser);
    userFollowRepository.save(userFollow);

    PushNotification pushNotification = userFollow.createPushNotification();
    pushNotificationRepository.save(pushNotification);

    NotificationDto notificationDto = pushNotification.createNotificationDto(targetUser);
    fcmService.sendMessage(notificationDto);
  }

  public List<FollowDto> getFollowing(Long loginUserId, Long targetUserId) {
    User loginUser = UserHelper.findUserById(userRepository, loginUserId);
    User targetUser = UserHelper.findUserById(userRepository, targetUserId);
    List<User> followingUserList = targetUser.getFollowingUserList();
    return FollowDto.listFrom(loginUser, followingUserList);
  }

  public List<FollowDto> getFollower(Long loginUserId, Long targetUserId) {
    User loginUser = UserHelper.findUserById(userRepository, loginUserId);
    User targetUser = UserHelper.findUserById(userRepository, targetUserId);
    List<User> followerUserList = targetUser.getFollowerUserList();
    return FollowDto.listFrom(loginUser, followerUserList);
  }

  public void unfollow(Long loginUserId, Long targetUserId) {
    UserFollow userFollow = userFollowRepository.findByFromUser_IdAndToUser_Id(loginUserId, targetUserId)
                                                .orElseThrow(() -> new CustomException(UserErrorCodeType.INVALID_FOLLOW_INFO));
    userFollow.delete();
    userFollowRepository.delete(userFollow);
  }
}

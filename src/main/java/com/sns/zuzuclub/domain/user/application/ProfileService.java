package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.controller.profile.dto.ProfileResponseDto;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileService {

  private final UserInfoRepository userInfoRepository;

  public ProfileResponseDto getUserProfile(Long loginUserId,Long userId) {
    UserInfo userInfo = UserHelper.findUserInfoById(userInfoRepository, userId);
    boolean isLoginUserProfile = loginUserId.equals(userId);
    return new ProfileResponseDto(userInfoRepository, userInfo, isLoginUserProfile);
  }
}

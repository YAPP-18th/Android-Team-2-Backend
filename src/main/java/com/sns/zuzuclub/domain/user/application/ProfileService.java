package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.controller.profile.dto.ProfileResponseDto;
import com.sns.zuzuclub.controller.profile.dto.ProfileSettingDto;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileService {

  private final UserService userService;
  private final UserRepository userRepository;

  public ProfileResponseDto getUserProfile(Long loginUserId, Long profileUserId) {
    User profileUser = UserHelper.findUserById(userRepository, profileUserId);
    return new ProfileResponseDto(profileUser, loginUserId);
  }

  public ProfileSettingDto getUserProfileSetting(Long userId) {
    User user = UserHelper.findUserById(userRepository, userId);
    return new ProfileSettingDto(user);
  }

  @Transactional
  public void updateProfile(Long userId, ProfileSettingDto profileSettingDto) {

    User user = UserHelper.findUserById(userRepository, userId);
    user.registerNickname(userService, profileSettingDto.getNickname());
    user.registerIntroduction(profileSettingDto.getIntroduction());
    user.registerProfileImageUrl(profileSettingDto.getProfileImageUrl());
  }
}

package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.controller.profile.dto.ProfileResponseDto;
import com.sns.zuzuclub.controller.profile.dto.ProfileSettingDto;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProfileService {

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

    // 다시 .. 도메인 서비스로 리팩토링 필요하네..
    boolean isDuplicatedNickname = userRepository.existsByNickname(profileSettingDto.getNickname());
    if(isDuplicatedNickname){
      throw new CustomException(UserErrorCodeType.DUPLICATE_NICKNAME);
    }

    User user = UserHelper.findUserById(userRepository, userId);
    user.registerNickname(profileSettingDto.getNickname());
    user.registerIntroduction(profileSettingDto.getIntroduction());
    user.registerProfileImageUrl(profileSettingDto.getProfileImageUrl());
  }
}

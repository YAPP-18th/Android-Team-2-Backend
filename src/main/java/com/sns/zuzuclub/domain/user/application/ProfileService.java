package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.controller.profile.dto.ProfileResponseDto;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileService {

  private final UserRepository userRepository;

  public ProfileResponseDto getUserProfile(Long loginUserId, Long profileUserId) {
    User profileUser = UserHelper.findUserById(userRepository, profileUserId);
    return new ProfileResponseDto(profileUser, loginUserId);
  }
}

package com.sns.zuzuclub.domain.user.service;

import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public void validateNickname(Long loginUserId, String nickname){
    Optional<User> targetUser = userRepository.findByNickname(nickname);
    targetUser.ifPresent(user -> {
      boolean isSameUser = user.getId().equals(loginUserId);
      if (!isSameUser) {
        throw new CustomException(UserErrorCodeType.DUPLICATE_NICKNAME);
      }
    });
  }

}

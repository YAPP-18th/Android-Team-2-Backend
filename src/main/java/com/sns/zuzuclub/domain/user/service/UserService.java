package com.sns.zuzuclub.domain.user.service;

import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

  private final UserRepository userRepository;

  public void isDuplicated(String nickname){
    boolean isDuplicatedNickname = userRepository.existsByNickname(nickname);
    if(isDuplicatedNickname){
      throw new CustomException(UserErrorCodeType.DUPLICATE_NICKNAME);
    }
  }

}

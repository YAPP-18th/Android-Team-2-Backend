package com.sns.zuzuclub.domain.user.helper;

import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;

public class UserHelper {

  public static User findUserById(UserRepository userRepository, Long id){
    return userRepository.findById(id)
                  .orElseThrow(() -> new CustomException(UserErrorCodeType.INVALID_USER));
  }

}

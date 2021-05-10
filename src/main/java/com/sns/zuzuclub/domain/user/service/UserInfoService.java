package com.sns.zuzuclub.domain.user.service;

import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoService {

  private final UserInfoRepository userInfoRepository;

  public boolean exist(Long userInfoId) {
    return userInfoRepository.findById(userInfoId)
                             .isPresent();
  }

  public boolean hasDuplicatedNickname(String nickname){
    return userInfoRepository.findByNickname(nickname).isPresent();
  }
}

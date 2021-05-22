package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.controller.search.dto.SearchUserResponseDto;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchUserService {

  private final UserInfoRepository userInfoRepository;

  public List<SearchUserResponseDto> searchByNickname(String nickname) {
    List<UserInfo> userInfoList = userInfoRepository.findAllByNicknameContaining(nickname);
    return SearchUserResponseDto.toListFrom(userInfoList);
  }
}

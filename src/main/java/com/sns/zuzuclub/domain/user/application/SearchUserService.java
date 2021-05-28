package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.controller.search.dto.SearchUserResponseDto;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchUserService {

  private final UserRepository userRepository;

  public List<SearchUserResponseDto> searchByNickname(String nickname) {
    List<User> userList = userRepository.findAllByNicknameStartingWith(nickname);
    return SearchUserResponseDto.toListOf(userList);
  }
}

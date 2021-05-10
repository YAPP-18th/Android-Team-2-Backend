package com.sns.zuzuclub.domain.user.service;

import com.sns.zuzuclub.domain.user.repository.UserSecurityRepository;
import com.sns.zuzuclub.global.exception.CustomException;

import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserSecurityRepository userSecurityRepository;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    return userSecurityRepository.findById(Long.valueOf(userId))
                                 .orElseThrow(()->new UsernameNotFoundException(UserErrorCodeType.INVALID_USER.getMessage()));
  }


}

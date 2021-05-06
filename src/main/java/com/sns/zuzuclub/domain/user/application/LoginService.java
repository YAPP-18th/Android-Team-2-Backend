package com.sns.zuzuclub.domain.user.application;

import com.sns.zuzuclub.constant.SocialTokenProviderType;
import com.sns.zuzuclub.constant.UserRoleType;
import com.sns.zuzuclub.controller.login.dto.LoginRequestDto;
import com.sns.zuzuclub.controller.login.dto.LoginResponseDto;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserSecurity;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.domain.user.repository.UserSecurityRepository;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.util.social.SocialTokenProviderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class LoginService {

  private final UserRepository userRepository;
  private final UserSecurityRepository userSecurityRepository;
  private final UserInfoRepository userInfoRepository;
  private final SocialTokenProviderFactory socialTokenProviderFactory;
  private final JwtTokenProvider jwtTokenProvider;

  @Transactional
  public LoginResponseDto login(LoginRequestDto loginRequestDto) {

    boolean needUserInfo = true;
    String socialToken = loginRequestDto.getSocialToken();
    SocialTokenProviderType provider = loginRequestDto.getProvider();

    String socialId = socialTokenProviderFactory.getTokenProvider(provider)
                                                .getSocialId(socialToken);
    UserSecurity userSecurityEntity = getUserSecurity(provider, socialId);

    if (userSecurityEntity == null) {
      // 소셜로그인 X, 회원정보입력 X
      User newUserEntity = userRepository.save(new User());

      String jwtAccessToken = jwtTokenProvider.createJwtAccessToken(newUserEntity.getId(), UserRoleType.USER);
      String jwtRefreshToken = jwtTokenProvider.createJwtRefreshToken(newUserEntity.getId());

      UserSecurity newUserSecurityEntity = UserSecurity.builder()
                                                       .user(newUserEntity)
                                                       .socialId(socialId)
                                                       .socialTokenProviderType(provider)
                                                       .jwtRefreshToken(jwtRefreshToken)
                                                       .userRoleType(UserRoleType.USER)
                                                       .build();
      userSecurityRepository.save(newUserSecurityEntity);

      return LoginResponseDto.builder()
                             .jwtAccessToken(jwtAccessToken)
                             .jwtRefreshToken(jwtRefreshToken)
                             .needUserInfo(needUserInfo)
                             .build();
    }

    if (existUserInfo(userSecurityEntity)) {
      // 소셜로그인 완료, 회원가입 완료
      // Ex) 다른 기기 로그인
      needUserInfo = false;
    }

    String jwtAccessToken = jwtTokenProvider.createJwtAccessToken(userSecurityEntity.getId(), UserRoleType.USER);
    String jwtRefreshToken = jwtTokenProvider.createJwtRefreshToken(userSecurityEntity.getId());

    return LoginResponseDto.builder()
                           .jwtAccessToken(jwtAccessToken)
                           .jwtRefreshToken(jwtRefreshToken)
                           .needUserInfo(needUserInfo)
                           .build();
  }

  private UserSecurity getUserSecurity(SocialTokenProviderType provider, String socialId) {
    return userSecurityRepository.findBySocialTokenProviderTypeAndSocialId(provider, socialId);
  }

  private boolean existUserInfo(UserSecurity userSecurityEntity) {
    return userInfoRepository.findById(userSecurityEntity.getId())
                             .isPresent();
  }
}

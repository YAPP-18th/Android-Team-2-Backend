package com.sns.zuzuclub.util.social;

import com.sns.zuzuclub.constant.SocialTokenProviderType;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.SocialLoginErrorCodeType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SocialTokenProviderFactory {

  public final List<SocialTokenProvider> socialTokenProviderList;

  public SocialTokenProvider getTokenProvider(SocialTokenProviderType provider) {
    return socialTokenProviderList.stream()
                                  .filter(socialTokenProvider -> socialTokenProvider.getProviderName()
                                                                                    .equals(provider.toString()))
                                  .findFirst()
                                  .orElseThrow(() -> new CustomException(SocialLoginErrorCodeType.INVALID_SOCIAL_PROVIDER));
  }
}

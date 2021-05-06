package com.sns.zuzuclub.util.social;

import org.springframework.http.ResponseEntity;

public interface SocialTokenProvider {
  String getProviderName();
  String getSocialId(String socialToken);
  ResponseEntity<String> requestUserInfo(String socialToken);
}

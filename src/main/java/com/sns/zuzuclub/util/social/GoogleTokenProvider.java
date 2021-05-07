package com.sns.zuzuclub.util.social;

import com.sns.zuzuclub.constant.SocialTokenProviderType;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.SocialLoginErrorCodeType;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleTokenProvider implements SocialTokenProvider{

  //  @Value("")
  //  private String client_Id;
  // 구글 파서로 리팩토링 필요

  @Override
  public String getProviderName() {
    return SocialTokenProviderType.GOOGLE.toString();
  }

  @Override
  public String getSocialId(String socialToken) {

    ResponseEntity<String> responseHttpEntity = requestUserInfo(socialToken);
    String response = responseHttpEntity.getBody();
    return String.valueOf(new JSONObject(response).getString("id"));
  }

  @Override
  public ResponseEntity<String> requestUserInfo(String socialToken){
    RestTemplate rest = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + socialToken);

    HttpEntity<String> requestEntity = new HttpEntity<>(headers);
    try {
      return rest.exchange("https://www.googleapis.com/oauth2/v2/userinfo", HttpMethod.GET, requestEntity, String.class);
    } catch (RestClientException e) {
      throw new CustomException(SocialLoginErrorCodeType.FAILED_SOCIAL_TOKEN_VALIDATION_CHECK);
    }
  }
}

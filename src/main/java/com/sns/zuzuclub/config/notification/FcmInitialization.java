package com.sns.zuzuclub.config.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
public class FcmInitialization {

  private static final String path = "fcm_service_account.json";

  @Bean
  public void initFcm() {
    try {
      FirebaseOptions options = FirebaseOptions.builder()
                                               .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(path).getInputStream()))
                                               .build();
      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
        log.info("Firebase APP initialized");
      }
    } catch (IOException e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
  }
}

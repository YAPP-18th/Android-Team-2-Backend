package com.sns.zuzuclub.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final String DEFAULT_TITLE = "ZUZU CLUB PROJECT";
  private static final String BASE_DOMAIN_PACKAGE = "com.sns.zuzuclub.domain";
  private static final String COMMENT_PACKAGE = BASE_DOMAIN_PACKAGE + ".comment";
  private static final String COMMON_PACKAGE = BASE_DOMAIN_PACKAGE + ".common";
  private static final String NOTIFICATION_PACKAGE = BASE_DOMAIN_PACKAGE + ".notification";
  private static final String POST_PACKAGE = BASE_DOMAIN_PACKAGE + ".post";
  private static final String STOCK_PACKAGE = BASE_DOMAIN_PACKAGE + ".stock";
  private static final String USER_PACKAGE = BASE_DOMAIN_PACKAGE + ".user";

  @Bean
  public Docket commentApiDocket() {
    return getDocket(COMMENT_PACKAGE);
  }

  @Bean
  public Docket commonApiDocket() {
    return getDocket(COMMON_PACKAGE);
  }

  @Bean
  public Docket notificationApiDocket() {
    return getDocket(NOTIFICATION_PACKAGE);
  }

  @Bean
  public Docket postApiDocket() {
    return getDocket(POST_PACKAGE);
  }

  @Bean
  public Docket stockApiDocket() {
    return getDocket(STOCK_PACKAGE);
  }

  @Bean
  public Docket userApiDocket() {
    return getDocket(USER_PACKAGE);
  }

  private Docket getDocket(String basePackage) {
    int idx = basePackage.lastIndexOf(".");
    String packageName = basePackage.substring(idx+1).toUpperCase();
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .groupName(packageName)
        .select()
        .apis(RequestHandlerSelectors.basePackage(basePackage))
        .build()
        .apiInfo(apiInfo(DEFAULT_TITLE));
  }

  private ApiInfo apiInfo(String description) {
    return new ApiInfoBuilder()
        .title("ZUZU CLUB API for Android Ants!")
        .description(description)
        .version("1.0")
        .build();
  }
}

package com.sns.zuzuclub.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  private static final String DEFAULT_TITLE = "ZUZU CLUB PROJECT - ";
  private static final String CONTROLLER_PACKAGE_NAME = "com.sns.zuzuclub.controller";
  private static final String COMMENT_PACAKGE = CONTROLLER_PACKAGE_NAME + ".comment";
  private static final String HOME_PACKAGE = CONTROLLER_PACKAGE_NAME + ".home";
  private static final String LOGIN_PACKAGE = CONTROLLER_PACKAGE_NAME + ".login";
  private static final String POST_PACKAGE = CONTROLLER_PACKAGE_NAME + ".post";
  private static final String PROFILE_PACKAGE = CONTROLLER_PACKAGE_NAME + ".profile";
  private static final String SEARCH_PACKAGE = CONTROLLER_PACKAGE_NAME + ".search";
  private static final String SIGNUP_PACKAGE = CONTROLLER_PACKAGE_NAME + ".signup";

  private String groupName;


  @Bean
  public Docket commentApiDocket() {
    groupName = "COMMENT";
    return getDocket(groupName, COMMENT_PACAKGE);
  }

  @Bean
  public Docket homeApiDocket() {
    groupName = "HOME";
    return getDocket(groupName, HOME_PACKAGE);
  }

  @Bean
  public Docket loginApiDocket() {
    groupName = "LOGIN";
    return getDocket(groupName, LOGIN_PACKAGE);
  }

  @Bean
  public Docket postApiDocket() {
    groupName = "POST";
    return getDocket(groupName, POST_PACKAGE);
  }

  @Bean
  public Docket profileApiDocket() {
    groupName = "PROFILE";
    return getDocket(groupName, PROFILE_PACKAGE);
  }

  @Bean
  public Docket searchApiDocket() {
    groupName = "SEARCH";
    return getDocket(groupName, SEARCH_PACKAGE);
  }


  @Bean
  public Docket signupApiDocket() {
    groupName = "SIGNUP";
    return getDocket(groupName, SIGNUP_PACKAGE);
  }

  private Docket getDocket(String groupName, String basePackage) {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .groupName(groupName)
        .select()
        .apis(RequestHandlerSelectors.basePackage(basePackage))
        .build()
        .apiInfo(apiInfo(DEFAULT_TITLE + groupName));
  }

  private ApiInfo apiInfo(String description) {
    return new ApiInfoBuilder()
        .title("ZUZU CLUB API for Android Ants!")
        .description(description)
        .version("1.0")
        .build();
  }
}

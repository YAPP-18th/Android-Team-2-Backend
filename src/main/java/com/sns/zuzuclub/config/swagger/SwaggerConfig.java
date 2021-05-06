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

    private final String basePackage = "com.sns.zuzuclub.controller";

    private ApiInfo apiInfo(String description) {
        return new ApiInfoBuilder()
            .title("ZUZU CLUB API for Android Ants!")
            .description(description)
            .version("1.0")
            .build();
    }

    @Bean
    public Docket getUserAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
            .groupName("USER_API")
            .apiInfo(apiInfo("회원과 관련된 기능입니다."))
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.ant("/user/**"))
            .build();
    }

    @Bean
    public Docket getPostAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .groupName("POST_API")
            .apiInfo(apiInfo("포스트와 관련된 기능입니다."))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sns.zuzuclub.controller.post"))
//            .paths(PathSelectors.ant("/post/**"))
            .build();
    }

    @Bean
    public Docket getCommentAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .groupName("COMMENT_API")
            .apiInfo(apiInfo("댓글과 관련된 기능입니다."))
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.sns.zuzuclub.controller.comment"))
            .paths(PathSelectors.ant("/comment/**"))
            .build();
    }
}

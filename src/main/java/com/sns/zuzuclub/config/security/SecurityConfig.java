package com.sns.zuzuclub.config.security;

import com.sns.zuzuclub.constant.UserRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtTokenProvider jwtTokenProvider;
  private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // rest api이므로 csrf 보안이 필요없으므로 disable처리.
        .csrf().disable()

        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증하므로 세션은 필요없으므로 생성안함.

        .and()
        .authorizeRequests()
        .antMatchers("/jwt/**").permitAll()
        .antMatchers("/exception/**").permitAll()
        .anyRequest().hasRole(UserRoleType.USER.name())

        .and()
        .exceptionHandling()
        .accessDeniedHandler(jwtAccessDeniedHandler)
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)

        .and()
        .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

  }

  // 스웨거 창은 제외시킨다.
  @Override
  public void configure(WebSecurity web) {
    web
        .ignoring()
        .antMatchers("/v2/api-docs/**", "/swagger-resources/**",
            "/swagger-ui.html/**", "/webjars/**", "/swagger/**");

  }
}

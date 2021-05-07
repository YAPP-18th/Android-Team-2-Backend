package com.sns.zuzuclub.config.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String jwtToken = httpServletRequest.getHeader("Authorization");

    if (jwtTokenProvider.isValidatedToken(jwtToken)) {
      // Authentication 객체에 인증정보를 넣어준다.
      // SecurityContextHolder에 Authentication을 등록한다.
      Authentication auth = jwtTokenProvider.getAuthentication(jwtToken);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    // 3. 실행.
    chain.doFilter(request, response);
  }
}

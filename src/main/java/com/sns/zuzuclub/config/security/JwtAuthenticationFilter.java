package com.sns.zuzuclub.config.security;

import com.sns.zuzuclub.global.exception.errorCodeType.JwtErrorCodeType;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String jwtAccessToken = httpServletRequest.getHeader("Authorization");

    if (isValidAccessToken(request, jwtAccessToken)){
      Authentication auth = jwtTokenProvider.getAuthentication(jwtAccessToken);
      SecurityContextHolder.getContext().setAuthentication(auth);
    }

    chain.doFilter(request, response);
  }

  private boolean isValidAccessToken(ServletRequest request, String jwtAccessToken) {
    try {
      jwtTokenProvider.validateAccessToken(jwtAccessToken);
      return true;
    } catch (ExpiredJwtException e) {
      request.setAttribute("exception", JwtErrorCodeType.EXPIRED_ACCESS_TOKEN);
      return false;
    } catch (RuntimeException e) {
      request.setAttribute("exception", JwtErrorCodeType.NOT_VALID_TOKEN);
      return false;
    }
  }
}

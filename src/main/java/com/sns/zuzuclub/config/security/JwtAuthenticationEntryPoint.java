package com.sns.zuzuclub.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.zuzuclub.global.exception.errorCodeType.JwtErrorCodeType;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final ObjectMapper OBJECT_MAPPER;

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

    JwtErrorCodeType jwtErrorCodeType = (JwtErrorCodeType) request.getAttribute("exception");
    String jsonString = OBJECT_MAPPER.writeValueAsString(jwtErrorCodeType);

    log.error("URL : {}", request.getRequestURL().toString());
    log.error("Security Error : {}", jsonString);

    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(jsonString);
  }
}

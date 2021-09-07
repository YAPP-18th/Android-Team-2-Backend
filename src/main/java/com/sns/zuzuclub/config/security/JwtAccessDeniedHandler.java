package com.sns.zuzuclub.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sns.zuzuclub.global.exception.errorCodeType.JwtErrorCodeType;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper OBJECT_MAPPER;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException, ServletException {

    String jsonString = OBJECT_MAPPER.writeValueAsString(JwtErrorCodeType.UNAUTHORIZED_JWT);

    log.error("URL : {}", request.getRequestURL());
    log.error("Authorization Error : {}", jsonString);

    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write(jsonString);
  }
}

package com.sns.zuzuclub.controller.login;

import com.sns.zuzuclub.controller.login.dto.LoginRequestDto;
import com.sns.zuzuclub.controller.login.dto.LoginResponseDto;
import com.sns.zuzuclub.controller.login.dto.ReissueJwtTokenRequestDto;
import com.sns.zuzuclub.controller.login.dto.ReissueJwtTokenResponseDto;
import com.sns.zuzuclub.domain.user.application.LoginService;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

  private final LoginService loginService;

  @PostMapping("/jwt")
  public SingleResult<LoginResponseDto> getJwtToken(@RequestBody LoginRequestDto loginRequestDto) {
    LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);
    return ResponseForm.getSingleResult(loginResponseDto, "jwt 토큰 발급");
  }

  @PostMapping("/jwt/access")
  public SingleResult<ReissueJwtTokenResponseDto> reissueJwtToken(@RequestBody ReissueJwtTokenRequestDto reissueJwtTokenRequestDto) {
    ReissueJwtTokenResponseDto reissueJwtTokenResponseDto = loginService.reissueJwtToken(reissueJwtTokenRequestDto);
    return ResponseForm.getSingleResult(reissueJwtTokenResponseDto, "jwt 토큰 재발급");
  }
}

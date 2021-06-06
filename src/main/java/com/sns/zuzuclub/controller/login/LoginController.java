package com.sns.zuzuclub.controller.login;

import com.sns.zuzuclub.controller.login.dto.LoginRequestDto;
import com.sns.zuzuclub.controller.login.dto.LoginResponseDto;
import com.sns.zuzuclub.controller.login.dto.ReissueJwtTokenResponseDto;
import com.sns.zuzuclub.domain.user.application.LoginService;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

  private final LoginService loginService;

  @ApiOperation(
      value = "JWT - ACCESS, REFRESH 토큰 발급",
      notes = "<h3>\n"
          + "- 첫 jwt 발급\n"
          + "- RefreshToken 만료되면, 다시 소셜로그인하고 처음부터 재발급\n"
          + "- 소셜로그인 연결만 하고, 회원가입을 완료하지않은 경우를 고려하기 위해서\n"
          + "  - needUserInfo가 true인 경우 => 회원가입 창으로 보내면 됩니다.\n"
          + "  - needUserInfo가 false인 경우 => 홈화면으로 보내면됩니다. Ex) 다른 기기로 로그인"
          + "</h3>"
  )
  @PostMapping("/jwt")
  public SingleResult<LoginResponseDto> getJwtToken(@RequestBody LoginRequestDto loginRequestDto) {
    log.info(loginRequestDto.toString());
    LoginResponseDto loginResponseDto = loginService.login(loginRequestDto);
    log.info(loginResponseDto.toString());
    return ResponseForm.getSingleResult(loginResponseDto, "jwt 토큰 발급");
  }


  @ApiOperation(
      value = "JWT - ACCESS, REFRESH 토큰 재발급",
      notes = "<h3>\n"
          + "- AccessToken 이 만료되면, RefeshToken을 이용해서 재발급 받습니다.\n"
          + "- AccessToken 은 항상 새로운 값이 리턴됩니다.\n"
          + "- RefreshToken 의 유효기간이 3일 보다 적게 남았으면, RefreshToken 도 재발급합니다.\n"
          + "- RefreshToken 의 유효기간이 3일 보다 많이 남았으면, 보냈던 것과 같은 RefreshToken 값이 리턴됩니다."
          + "</h3>"
  )
  @PostMapping("/jwt/refresh")
  public SingleResult<ReissueJwtTokenResponseDto> reissueJwtToken(@ApiParam(value = "JWT - RefreshToken") @RequestBody String jwtRefreshToken) {
    jwtRefreshToken = jwtRefreshToken.replaceAll("\"", "");
    log.info(jwtRefreshToken);
    ReissueJwtTokenResponseDto reissueJwtTokenResponseDto = loginService.reissueJwtToken(jwtRefreshToken);
    log.info(reissueJwtTokenResponseDto.toString());
    return ResponseForm.getSingleResult(reissueJwtTokenResponseDto, "jwt 토큰 재발급");
  }
}

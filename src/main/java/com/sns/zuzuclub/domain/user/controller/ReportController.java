package com.sns.zuzuclub.domain.user.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.domain.user.application.ReportService;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ReportController {

  private final JwtTokenProvider jwtTokenProvider;
  private final ReportService reportService;

  @ApiOperation(
      value = "신고 - 다른 유저를 신고",
      notes = "<h3>\n"
          + "- 유저 닉네임으로 다른 유저를 신고합니다.\n"
          + "</h3>"
  )
  @PostMapping("/report/{nickname}")
  public CommonResult report(@RequestHeader(value = "Authorization") String jwtToken,
                             @PathVariable String nickname){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    reportService.report(userId, nickname);
    log.info("{} 신고 성공", nickname);
    return ResponseForm.getSuccessResult(nickname + "신고 성공");
  }

  @ApiOperation(
      value = "신고 - 다른 유저가 나를 신고했는지 판단하는 API(nickname)",
      notes = "<h3>\n"
          + "- 타 유저가 본인을 신고했는지 판단하는 API\n"
          + "- Path Variable 에 다른 유저의 닉네임이 들어갑니다.\n"
          + "- true 이면 신고한 응답입니다.\n"
          + "</h3>"
  )
  @GetMapping("/report/me/nickname/{nickname}")
  public SingleResult<Boolean> isBlockedByNickname(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable String nickname){
    Long loginUserId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    Boolean isBlocked = reportService.isBlockedByNickname(loginUserId, nickname);
    log.info("닉네임 {} 가 로그인 유저를 신고했는지 확인", nickname);
    return ResponseForm.getSingleResult(isBlocked, String.format("닉네임 %s 가 로그인 유저를 신고했는지 확인", nickname));
  }

  @ApiOperation(
      value = "신고 - 다른 유저가 나를 신고했는지 판단하는 API(userId)",
      notes = "<h3>\n"
          + "- 타 유저가 본인을 신고했는지 판단하는 API\n"
          + "- Path Variable 에 다른 유저의 userId가 들어갑니다.\n"
          + "- true 이면 신고한 응답입니다.\n"
          + "</h3>"
  )
  @GetMapping("/report/me/id/{userId}")
  public SingleResult<Boolean> isBlockedByUserId(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long userId){
    Long loginUserId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    Boolean isBlocked = reportService.isBlockedByUserId(loginUserId, userId);
    log.info("userId {} 이 로그인 유저를 신고했는지 확인", userId);
    return ResponseForm.getSingleResult(isBlocked, String.format("userId %s 가 로그인 유저를 신고했는지 확인", userId));
  }

}

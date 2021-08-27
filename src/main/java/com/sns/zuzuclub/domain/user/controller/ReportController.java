package com.sns.zuzuclub.domain.user.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.domain.user.application.ReportService;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

}

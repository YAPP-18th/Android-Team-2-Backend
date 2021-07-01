package com.sns.zuzuclub.controller.follow;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.constant.PostReactionType;
import com.sns.zuzuclub.controller.post.dto.CreatePostReactionResponseDto;
import com.sns.zuzuclub.domain.user.application.FollowService;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
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
public class followController {

  private final JwtTokenProvider jwtTokenProvider;
  private final FollowService followService;

  @ApiOperation(
      value = "다른 유저를 팔로우",
      notes = "<h3>\n"
          + "- targetUserId의 유저를 팔로우합니다.\n"
          + "</h3>"
  )
  @PostMapping("/follow/{targetUserId}")
  public CommonResult follow(@RequestHeader(value = "Authorization") String jwtToken,
                             @PathVariable Long targetUserId) {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    followService.follow(userId, targetUserId);
    log.info(targetUserId + "팔로우 성공");
    return ResponseForm.getSuccessResult("다른 유저를 팔로우");
  }

}

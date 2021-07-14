package com.sns.zuzuclub.domain.user.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.domain.user.dto.follow.FollowDto;
import com.sns.zuzuclub.domain.user.application.FollowService;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.MultipleResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
          + "- userId의 유저를 팔로우합니다.\n"
          + "</h3>"
  )
  @PostMapping("/users/{userId}/follower")
  public CommonResult follow(@RequestHeader(value = "Authorization") String jwtToken,
                             @PathVariable("userId") Long targetUserId) {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    followService.follow(userId, targetUserId);
    log.info(targetUserId + "팔로우 성공");
    return ResponseForm.getSuccessResult("다른 유저를 팔로우");
  }

  @ApiOperation(
      value = "팔로잉 목록 조회",
      notes = "<h3>\n"
          + "- userId를 갖는 유저의 팔로잉 목록을 조회합니다.\n"
          + "</h3>"
  )
  @GetMapping("/users/{userId}/following")
  public MultipleResult<FollowDto> getFollowing(@RequestHeader(value = "Authorization") String jwtToken,
                                               @PathVariable("userId") Long targetUserId) {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    List<FollowDto> followDtoList = followService.getFollowing(userId, targetUserId);
    log.info(followDtoList.toString());
    return ResponseForm.getMultipleResult(followDtoList, "팔로잉 목록 조회");
  }

  @ApiOperation(
      value = "팔로워 목록 조회",
      notes = "<h3>\n"
          + "- userId를 갖는 유저의 팔로워 목록을 조회합니다.\n"
          + "</h3>"
  )
  @GetMapping("/users/{userId}/follower")
  public MultipleResult<FollowDto> getFollower(@RequestHeader(value = "Authorization") String jwtToken,
                                               @PathVariable("userId") Long targetUserId) {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    List<FollowDto> followDtoList = followService.getFollower(userId, targetUserId);
    log.info(followDtoList.toString());
    return ResponseForm.getMultipleResult(followDtoList, "팔로우 목록 조회");
  }

  @ApiOperation(
      value = "팔로우 취소",
      notes = "<h3>\n"
          + "- userId를 갖는 유저에 대해서 팔로우를 취소합니다.\n"
          + "</h3>"
  )
  @DeleteMapping("/users/{userId}/follower")
  public CommonResult unfollow(@RequestHeader(value = "Authorization") String jwtToken,
                                               @PathVariable("userId") Long targetUserId) {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    followService.unfollow(userId, targetUserId);
    log.info("팔로우 취소");
    return ResponseForm.getSuccessResult("팔로우 취소");
  }
}

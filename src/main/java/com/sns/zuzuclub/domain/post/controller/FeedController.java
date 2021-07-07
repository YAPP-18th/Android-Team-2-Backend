package com.sns.zuzuclub.domain.post.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.domain.post.model.FeedType;
import com.sns.zuzuclub.domain.post.dto.FeedResponseDto;
import com.sns.zuzuclub.domain.post.application.FeedService;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FeedController {

  private final FeedService feedService;
  private final JwtTokenProvider jwtTokenProvider;

  @ApiOperation(
      value = "피드 불러오기 - ALL, HOT, FRIENDS",
      notes = "<h3>\n"
          + "- 피드의 각 타입별 게시글을 불러옵니다.\n"
          + "  - ALL : 싸그리깡그리모조리모두다불태워버려\n"
          + "  - HOT : 일주일동안, 게시글에 달린 반응이 많은 순서대로\n"
          + "  - FRIENDS : 팔로워들의 게시글들을 최신순으로 불러옵니다.\n"
          + "- page는 0부터 시작해서, 20개씩 최신순으로 불러옵니다.\n"
          + "</h3>"
  )
  @GetMapping("/posts")
  public SingleResult<FeedResponseDto> getFeed(@RequestHeader(value = "Authorization") String jwtToken, @RequestParam FeedType feedType, @RequestParam int page){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    FeedResponseDto feedResponseDto = feedService.getFeed(userId, feedType, page);
    log.info(feedResponseDto.toString());
    return ResponseForm.getSingleResult(feedResponseDto, "모든 게시글 가져오기 / page = " + page);
  }
}

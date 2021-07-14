package com.sns.zuzuclub.domain.post.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.domain.post.model.PostReactionType;
import com.sns.zuzuclub.domain.post.dto.CreatePostReactionResponseDto;
import com.sns.zuzuclub.domain.post.dto.ReactionDto;
import com.sns.zuzuclub.domain.post.application.PostReactionService;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostReactionController {

  private final PostReactionService postReactionService;
  private final JwtTokenProvider jwtTokenProvider;

  @ApiOperation(
      value = "피드 - 게시글 상세 - 반응하기",
      notes = "<h3>\n"
          + "- 게시글에 반응 합니다.\n"
          + "</h3>"
  )
  @PostMapping("/posts/{postId}/reaction/{postReactionType}")
  public SingleResult<CreatePostReactionResponseDto> createPostReaction(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long postId, @PathVariable PostReactionType postReactionType){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    CreatePostReactionResponseDto createPostReactionResponseDto  = postReactionService.createPostReaction(postId, postReactionType, userId);
    log.info(createPostReactionResponseDto.toString());
    return ResponseForm.getSingleResult(createPostReactionResponseDto,"게시글 상세 - 반응하기" );
  }

  @ApiOperation(
      value = "피드 - 게시글 - 반응목록 조회",
      notes = "<h3>\n"
          + "- postId를 갖는 게시글에 반응한 유저들을 조회합니다.\n"
          + "</h3>"
  )
  @GetMapping("/posts/{postId}/reactions")
  public SingleResult<ReactionDto> getPostReaction(@RequestHeader(value = "Authorization") String jwtToken,
                                                   @PathVariable Long postId) {
    ReactionDto reactionDto = postReactionService.getPostReaction(postId);
    log.info(reactionDto.toString());
    return ResponseForm.getSingleResult(reactionDto, "게시글 상세 - 반응하기" );
  }

  @ApiOperation(
      value = "피드 - 게시글 상세 - 반응취소",
      notes = "<h3>\n"
          + "- 게시글에 반응을 취소합니다.\n"
          + "</h3>"
  )
  @DeleteMapping("/posts/{postId}/reaction")
  public CommonResult deletePostReaction(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long postId){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    postReactionService.deletePostReaction(postId, userId);
    log.info("게시글 상세 - 반응 취소");
    return ResponseForm.getSuccessResult("게시글 상세 - 반응취소" );
  }

}

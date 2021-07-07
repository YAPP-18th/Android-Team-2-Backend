package com.sns.zuzuclub.domain.comment.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.domain.comment.model.CommentReactionType;
import com.sns.zuzuclub.domain.comment.dto.CreateCommentReactionResponseDto;
import com.sns.zuzuclub.domain.comment.application.CommentReactionService;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentReactionController {

  private final CommentReactionService commentReactionService;
  private final JwtTokenProvider jwtTokenProvider;

  @ApiOperation(
      value = "댓글 반응 작성",
      notes = "<h3>\n"
          + "- 댓글에 반응합니다.\n"
          + "</h3>"
  )
  @PostMapping("/comments/{commentId}/reactions/{commentReactionType}")
  public SingleResult<CreateCommentReactionResponseDto> createCommentReaction(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long commentId, @PathVariable CommentReactionType commentReactionType){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    CreateCommentReactionResponseDto createCommentReactionResponseDto = commentReactionService.createCommentReaction(userId, commentId, commentReactionType);
    log.info(createCommentReactionResponseDto.toString());
    return ResponseForm.getSingleResult(createCommentReactionResponseDto, "댓글 반응 작성");
  }

  @ApiOperation(
      value = "댓글 반응 취소",
      notes = "<h3>\n"
          + "- 댓글에 반응을 취소합니다.\n"
          + "</h3>"
  )
  @DeleteMapping("/comments/{commentId}/reactions")
  public CommonResult deleteCommentReaction(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long commentId){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    commentReactionService.deleteCommentReaction(userId, commentId);
    log.info("댓글 반응 취소");
    return ResponseForm.getSuccessResult("댓글 반응 취소");
  }
}

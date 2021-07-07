package com.sns.zuzuclub.controller.comment;


import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.constant.CommentReactionType;
import com.sns.zuzuclub.controller.comment.dto.CreateCommentReactionResponseDto;
import com.sns.zuzuclub.controller.comment.dto.CreateCommentRequestDto;
import com.sns.zuzuclub.controller.comment.dto.CreateCommentResponseDto;
import com.sns.zuzuclub.domain.comment.application.CommentService;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

  private final CommentService commentService;
  private final JwtTokenProvider jwtTokenProvider;

  @ApiOperation(
      value = "댓글 작성",
      notes = "<h3>\n"
          + "- 부모 댓글 없으면(=대댓글아니면) 부모 댓글 id 는 null로 설정\n"
          + "</h3>"
  )
  @PostMapping("/posts/{postId}/comment")
  public SingleResult<CreateCommentResponseDto> createComment(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long postId, @RequestBody CreateCommentRequestDto createCommentRequestDto){
    log.info(createCommentRequestDto.toString());
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    CreateCommentResponseDto createCommentResponseDto = commentService.createComment(userId, postId, createCommentRequestDto);
    log.info(createCommentResponseDto.toString());
    return ResponseForm.getSingleResult(createCommentResponseDto, "댓글 작성");
  }

  @ApiOperation(
      value = "댓글 삭제",
      notes = "<h3>\n"
          + "- 댓글의 내용을 \"삭제된 댓글 입니다.\" 로 수정합니다.\n"
          + "- 즉, 실제로 댓글을 삭제하지는 않습니다.\n"
          + "- 댓글은 게시글이 삭제되는 경우에만 완전하게 삭제됩니다.\n"
          + "</h3>"
  )
  @DeleteMapping("/posts/comments/{commentId}")
  public CommonResult deleteComment(@RequestHeader(value = "Authorization") String jwtToken,
                                                              @PathVariable Long commentId) {
    commentService.deleteComment(commentId);
    log.info(commentId + "번 댓글 삭제");
    return ResponseForm.getSuccessResult("댓글 삭제");
  }
}

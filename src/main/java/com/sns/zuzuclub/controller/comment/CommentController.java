package com.sns.zuzuclub.controller.comment;


import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.controller.comment.dto.CreateCommentRequestDto;
import com.sns.zuzuclub.controller.comment.dto.CreateCommentResponseDto;
import com.sns.zuzuclub.domain.comment.application.CommentService;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/post/{postId}")
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
  @PostMapping("/comment")
  public SingleResult<CreateCommentResponseDto> createComment(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long postId, @RequestBody CreateCommentRequestDto createCommentRequestDto){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    CreateCommentResponseDto createCommentResponseDto = commentService.createComment(userId, postId, createCommentRequestDto);
    return ResponseForm.getSingleResult(createCommentResponseDto, "댓글 작성");
  }

}

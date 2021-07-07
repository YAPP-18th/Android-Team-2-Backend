package com.sns.zuzuclub.domain.post.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.domain.post.dto.CreatePostRequestDto;
import com.sns.zuzuclub.domain.post.dto.CreatePostResponseDto;
import com.sns.zuzuclub.domain.post.dto.ModifyPostRequestDto;
import com.sns.zuzuclub.domain.post.dto.PostDetailResponseDto;

import com.sns.zuzuclub.domain.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.post.application.PostService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(
        value = "게시물 작성하기",
        notes = "<h3>\n"
            + "- 게시물을 작성합니다.\n"
            + "\n"
            + "</h3>"
    )
    @PostMapping("/posts")
    public SingleResult<CreatePostResponseDto> createPost(@RequestHeader(value = "Authorization") String jwtToken,
                                                          @RequestBody CreatePostRequestDto createPostRequestDto)
    {
        log.info(createPostRequestDto.toString());
        Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
        CreatePostResponseDto createPostResponseDto = postService.createPost(userId, createPostRequestDto);
        log.info(createPostResponseDto.toString());
        return ResponseForm.getSingleResult(createPostResponseDto,"게시물 작성");
    }

    @ApiOperation(
        value = "게시물 상세 조회",
        notes = "<h3>\n"
        + "- 게시글 상세정보를 불러옵니다.\n"
        + "- page는 0부터 시작해서, 20개씩 최신순으로 불러옵니다.\n"
        + "</h3>"
    )
    @GetMapping("/posts/{postId}")
    public SingleResult<PostDetailResponseDto> getPostDetail(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long postId){
        Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
        PostDetailResponseDto postDetailResponseDto = postService.getPostDetail(postId, userId);
        log.info(postDetailResponseDto.toString());
        return ResponseForm.getSingleResult(postDetailResponseDto,"게시글 상세 가져오기" );
    }

    @ApiOperation(
        value = "게시물 수정",
        notes = "<h3>\n"
            + "- 게시물 id를 받아서 게시물을 수정합니다.\n"
            + "- **[수정 가능 목록]**\n"
            + "  - 이미지 url\n"
            + "  - 게시글 내용\n"
            + "  - 게시글 감정\n"
            + "  - 언급한 주식들의 종목명\n"
            + "</h3>"
    )
    @PutMapping("/posts/{postId}")
    public SingleResult<PostResponseDto> modifyPost(@RequestHeader(value = "Authorization") String jwtToken,
                                                    @PathVariable Long postId,
                                                    @RequestBody ModifyPostRequestDto modifyPostRequestDto) {
        log.info(modifyPostRequestDto.toString());
        Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
        PostResponseDto postResponseDto = postService.modifyPost(userId, postId,
                                                                 modifyPostRequestDto);
        log.info(postResponseDto.toString());
        return ResponseForm.getSingleResult(postResponseDto, "게시물 수정");
    }

    @ApiOperation(
        value = "게시물 삭제",
        notes = "<h3>\n"
            + "- 게시물 id를 받아서 게시물을 삭제합니다.\n"
            + "</h3>"
    )
    @DeleteMapping("/posts/{postId}")
    public CommonResult deletePost(@RequestHeader(value = "Authorization") String jwtToken,
                                   @PathVariable Long postId) {
        postService.deletePost(postId);
        log.info(postId + "번 게시물 삭제 완료");
        return ResponseForm.getSuccessResult("게시물 삭제");
    }
}

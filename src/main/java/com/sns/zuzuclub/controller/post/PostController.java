package com.sns.zuzuclub.controller.post;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.controller.post.dto.CreatePostRequestDto;
import com.sns.zuzuclub.controller.post.dto.CreatePostResponseDto;
import com.sns.zuzuclub.controller.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.post.application.FeedService;
import com.sns.zuzuclub.global.response.MultipleResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final FeedService feedService;
    private final JwtTokenProvider jwtTokenProvider;

    @ApiOperation(
        value = "게시물 작성하기",
        notes = "<h3>\n"
            + "- 게시물을 작성합니다.\n"
            + "\n"
            + "</h3>"
    )
    @PostMapping("/post")
    public SingleResult<CreatePostResponseDto> createPost(@RequestHeader(value = "Authorization") String jwtToken, @RequestBody CreatePostRequestDto createPostRequestDto) {
        Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
        CreatePostResponseDto createPostResponseDto = feedService.createPost(userId, createPostRequestDto);
        return ResponseForm.getSingleResult(createPostResponseDto,"게시물 작성");
    }

    @ApiOperation(
        value = "피드 - 모든 게시글 불러오기",
        notes = "<h3>\n"
            + "- 피드의 ALL 게시글 불러옵니다.\n"
            + "- page는 0부터 시작해서, 20개씩 최신순으로 불러옵니다.\n"
            + "</h3>"
    )
    @GetMapping("/posts")
    public MultipleResult<PostResponseDto> getAllPost(@RequestHeader(value = "Authorization") String jwtToken, @RequestParam int page){
        List<PostResponseDto> postResponseDtoList = feedService.getAllPost(page);
        return ResponseForm.getMultipleResult(postResponseDtoList,"모든 게시글 가져오기 / page = " + page);
    }


    @PostMapping("/post")
    @ApiOperation(value = "게시물 전체 조회", notes = "특정 게시물의 댓글을 조회하는 기능")
    public PostRequestDto savePost(PostRequestDto postRequestDto) {
        feedService.save(postRequestDto);
        return postRequestDto;
    }




    @GetMapping("/posts/{postId}")
    @ApiOperation(value = "게시물 디테일 조회", notes = "특정 게시물을 조회하는 기능")
    public PostResponseDto getDetailPost(@PathVariable("postId") Long postId) {
        PostResponseDto post = feedService.findById(postId);
        return post;
    }
}

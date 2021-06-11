package com.sns.zuzuclub.controller.post;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.constant.FeedType;
import com.sns.zuzuclub.constant.PostReactionType;
import com.sns.zuzuclub.controller.post.dto.CreatePostReactionResponseDto;
import com.sns.zuzuclub.controller.post.dto.CreatePostRequestDto;
import com.sns.zuzuclub.controller.post.dto.CreatePostResponseDto;
import com.sns.zuzuclub.controller.post.dto.FeedResponseDto;
import com.sns.zuzuclub.controller.post.dto.PostDetailResponseDto;
import com.sns.zuzuclub.domain.post.application.FeedService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
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
    @PostMapping("/posts")
    public SingleResult<CreatePostResponseDto> createPost(@RequestHeader(value = "Authorization") String jwtToken,
                                                          @RequestBody CreatePostRequestDto createPostRequestDto)
    {
        log.info(createPostRequestDto.toString());
        Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
        CreatePostResponseDto createPostResponseDto = feedService.createPost(userId, createPostRequestDto);
        log.info(createPostResponseDto.toString());
        return ResponseForm.getSingleResult(createPostResponseDto,"게시물 작성");
    }

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
        return ResponseForm.getSingleResult(feedResponseDto,"모든 게시글 가져오기 / page = " + page);
    }

    @ApiOperation(
        value = "피드 - 게시글 상세",
        notes = "<h3>\n"
        + "- 게시글 상세정보를 불러옵니다.\n"
        + "- page는 0부터 시작해서, 20개씩 최신순으로 불러옵니다.\n"
        + "</h3>"
    )
    @GetMapping("/posts/{postId}")
    public SingleResult<PostDetailResponseDto> getPostDetail(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long postId){
        Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
        PostDetailResponseDto postDetailResponseDto = feedService.getPostDetail(postId, userId);
        log.info(postDetailResponseDto.toString());
        return ResponseForm.getSingleResult(postDetailResponseDto,"게시글 상세 가져오기" );
    }

    @ApiOperation(
        value = "피드 - 게시글 상세 - 반응하기",
        notes = "<h3>\n"
            + "- 게시글에 반응 합니다.\n"
            + "</h3>"
    )
    @PostMapping("/posts/{postId}/{postReactionType}")
    public SingleResult<CreatePostReactionResponseDto> createPostReaction(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long postId, @PathVariable PostReactionType postReactionType){
        Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
        CreatePostReactionResponseDto createPostReactionResponseDto  = feedService.createPostReaction(postId, postReactionType, userId);
        log.info(createPostReactionResponseDto.toString());
        return ResponseForm.getSingleResult(createPostReactionResponseDto,"게시글 상세 - 반응하기" );
    }

    @ApiOperation(
        value = "피드 - 게시글 상세 - 반응취소",
        notes = "<h3>\n"
            + "- 게시글에 반응을 취소합니다.\n"
            + "</h3>"
    )
    @DeleteMapping("/posts/{postId}")
    public CommonResult deletePostReaction(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable Long postId){
        Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
        feedService.deletePostReaction(postId, userId);
        log.info("게시글 상세 - 반응 취소");
        return ResponseForm.getSuccessResult("게시글 상세 - 반응취소" );
    }
}

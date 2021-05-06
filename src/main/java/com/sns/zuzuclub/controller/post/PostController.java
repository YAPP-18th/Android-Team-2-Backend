package com.sns.zuzuclub.controller.post;

import com.sns.zuzuclub.dto.post.PostRequestDto;
import com.sns.zuzuclub.dto.post.PostResponseDto;
import com.sns.zuzuclub.service.post.PostService;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/post")
    @ApiOperation(value = "게시물 전체 조회", notes = "특정 게시물의 댓글을 조회하는 기능")
    public PostRequestDto savePost(PostRequestDto postRequestDto) {
        postService.save(postRequestDto);
        return postRequestDto;
    }

    @GetMapping("/posts")
    @ApiOperation(value = "게시물 작성", notes = "게시을 등록하는 기능")
    public List<PostResponseDto> getAllPost() {
        List<PostResponseDto> posts = postService.findAll();
        return posts;
    }


    @GetMapping("/posts/{postId}")
    @ApiOperation(value = "게시물 디테일 조회", notes = "특정 게시물을 조회하는 기능")
    public PostResponseDto getDetailPost(@PathVariable("postId") Long postId) {
        PostResponseDto post = postService.findById(postId);
        return post;
    }


}

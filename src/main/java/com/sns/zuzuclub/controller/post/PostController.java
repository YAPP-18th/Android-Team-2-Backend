package com.sns.zuzuclub.controller.post;

import com.sns.zuzuclub.dto.post.PostRequestDto;
import com.sns.zuzuclub.dto.post.PostResponseDto;
import com.sns.zuzuclub.service.PostService;
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
    public PostRequestDto savePost(PostRequestDto postRequestDto) {
        postService.save(postRequestDto);
        return postRequestDto;
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getAllPost() {
        List<PostResponseDto> posts = postService.findAll();
        return posts;
    }

    @GetMapping("/posts/{postId}")
    public PostResponseDto getDetailPost(@PathVariable("postId") Long postId) {
        PostResponseDto post = postService.findById(postId);
        return post;
    }


}

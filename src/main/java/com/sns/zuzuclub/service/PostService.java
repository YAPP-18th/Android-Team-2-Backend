package com.sns.zuzuclub.service;

import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.dto.post.PostRequestDto;
import com.sns.zuzuclub.dto.post.PostResponseDto;
import com.sns.zuzuclub.repository.PostRepository;
import com.sns.zuzuclub.utils.MapperConfig;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(p -> modelMapper.map(p, PostResponseDto.class))
            .collect(Collectors.toList());
    }

    public PostResponseDto findById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NullPointerException());
        return modelMapper.map(post, PostResponseDto.class);
    }

    public Post save(PostRequestDto postRequestDto) {
        Post post = Post.builder()
            .content(postRequestDto.getContent())
            .postImageUrl(postRequestDto.getPostImageUrl())
            .postEmotionType(postRequestDto.getPostEmotionType())
            .build();
        postRepository.save(post);
        return post;
    }

    public Long delete(Post post) {
        postRepository.delete(post);
        return post.getId();
    }

}

package com.sns.zuzuclub.domain.post.application;

import com.sns.zuzuclub.controller.post.dto.CreatePostRequestDto;
import com.sns.zuzuclub.controller.post.dto.CreatePostResponseDto;
import com.sns.zuzuclub.controller.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final UserInfoRepository userInfoRepository;

  public List<PostResponseDto> getAllPost(int page) {
    Pageable pageable = PageRequest.of(page, 20, Sort.by("createdAt").descending());
    List<Post> postList = postRepository.findAll(pageable).getContent();
    return postList.stream()
                   .map(post -> {
                     UserInfo userInfo = UserHelper.findUserInfoById(userInfoRepository, post.getUser().getId());
                     return new PostResponseDto(userInfo, post);
                   })
                   .collect(Collectors.toList());
  }

  public CreatePostResponseDto createPost(Long userId, CreatePostRequestDto createPostRequestDto) {
    User user = UserHelper.findUserById(userRepository, userId);
    Post newPostEntity = postRepository.save(createPostRequestDto.toPostEntity(user));
    // 미완성
    return null;
  }
}

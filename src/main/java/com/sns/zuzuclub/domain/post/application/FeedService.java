package com.sns.zuzuclub.domain.post.application;

import com.sns.zuzuclub.constant.FeedType;
import com.sns.zuzuclub.controller.post.dto.FeedResponseDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.PostErrorCodeType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeedService {

  private final UserRepository userRepository;
  private final PostRepository postRepository;

  public FeedResponseDto getFeed(Long userId, FeedType feedType, int page) {
    // querydsl 로 리팩토링 필요
    // userId는 나중에 차단 먹일 때, 사용
    switch (feedType){
      case ALL:
        return this.getAllPost(userId, page);
      case HOT:
        return this.getHotPost(userId, page);
      case FRIENDS:
        return this.getFriendsPost(userId, page);
      default:
        throw new CustomException(PostErrorCodeType.INVALID_FEED_TYPE);
    }
  }

  private FeedResponseDto getAllPost(Long userId, int page) {
    Pageable pageable = PageRequest.of(page, 20, Sort.by("createdAt").descending());
    Page<Post> postPage = postRepository.findAll(pageable);
    return new FeedResponseDto(postPage, userId);
  }

  private FeedResponseDto getHotPost(Long userId, int page) {
    Pageable pageable = PageRequest.of(page, 20, Sort.by("postReactionCount").descending());
    LocalDateTime from = LocalDateTime.now().minusDays(7); // 7일 전
    Page<Post> postPage = postRepository.findByCreatedAtAfter(from, pageable);
    return new FeedResponseDto(postPage, userId);
  }

  private FeedResponseDto getFriendsPost(Long userId, int page) {

    User user = UserHelper.findUserById(userRepository, userId);
    List<User> followingUserList = user.getFollowingUserList();

    Pageable pageable = PageRequest.of(page, 20, Sort.by("createdAt").descending());
    Page<Post> postPage = postRepository.findAllByUserIn(followingUserList, pageable);
    return new FeedResponseDto(postPage, userId);
  }

}

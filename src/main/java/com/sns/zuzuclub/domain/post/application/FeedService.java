package com.sns.zuzuclub.domain.post.application;

import com.sns.zuzuclub.constant.FeedType;
import com.sns.zuzuclub.constant.PostReactionType;
import com.sns.zuzuclub.controller.post.dto.CreatePostReactionResponseDto;
import com.sns.zuzuclub.controller.post.dto.CreatePostRequestDto;
import com.sns.zuzuclub.controller.post.dto.CreatePostResponseDto;
import com.sns.zuzuclub.controller.post.dto.PostDetailResponseDto;
import com.sns.zuzuclub.controller.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.post.helper.PostHelper;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.model.PostReaction;
import com.sns.zuzuclub.domain.post.repository.PostReactionRepository;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.post.service.PostReactionService;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.PostErrorCodeType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FeedService {

  private final PostReactionService postReactionService;

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final PostReactionRepository postReactionRepository;
  private final StockRepository stockRepository;


  @Transactional
  public CreatePostResponseDto createPost(Long userId, CreatePostRequestDto createPostRequestDto) {

    User userEntity = UserHelper.findUserById(userRepository, userId);
    Post newPostEntity = createPostRequestDto.toPostEntity(userEntity);

    List<Stock> requestStockList = stockRepository.findAllById(createPostRequestDto.getRequestStockIdList());
    newPostEntity.setPostedStock(requestStockList);

    return new CreatePostResponseDto(postRepository.save(newPostEntity));
  }

  public List<PostResponseDto> getFeed(Long userId, FeedType feedType, int page) {
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

  public List<PostResponseDto> getAllPost(Long userId, int page) {
    Pageable pageable = PageRequest.of(page, 20, Sort.by("createdAt").descending());
    List<Post> postList = postRepository.findAll(pageable).getContent();
    return PostResponseDto.ListFrom(postList, userId);
  }

  public List<PostResponseDto> getHotPost(Long userId, int page) {
    Pageable pageable = PageRequest.of(page, 20, Sort.by("postReactionCount").descending());
    LocalDateTime from = LocalDateTime.now().minusDays(7); // 7일 전
    List<Post> postList = postRepository.findByCreatedAtAfter(from, pageable);
    return PostResponseDto.ListFrom(postList, userId);
  }

  public List<PostResponseDto> getFriendsPost(Long userId, int page) {

    User user = UserHelper.findUserById(userRepository, userId);
    List<User> followingUserList = user.getMyFollowingUserList();

    Pageable pageable = PageRequest.of(page, 20, Sort.by("createdAt").descending());
    List<Post> postList = postRepository.findAllByUserIn(followingUserList, pageable);
    return PostResponseDto.ListFrom(postList, userId);

    // TODO : 피드 목록에서 자기꺼확인여부 생성자 분리해야함 + 리포지토리 빼내려면 User를 합치는게 나을듯
  }

  public PostDetailResponseDto getPostDetail(Long postId, Long loginUserId) {
    Post postEntity = PostHelper.findPostById(postRepository, postId);
    return new PostDetailResponseDto(postEntity, loginUserId);
    // TODO : 피드 목록에서 자기꺼확인여부 생성자 분리해야함 + 리포지토리 빼내려면 User를 합치는게 나을듯
  }

  @Transactional
  public CreatePostReactionResponseDto createPostReaction(Long postId, PostReactionType postReactionType, Long userId) {
    User user = UserHelper.findUserById(userRepository, userId);
    Post post = PostHelper.findPostById(postRepository, postId);
    PostReaction postReaction = new PostReaction(user, post, postReactionType);
    postReactionRepository.save(postReaction);
    return new CreatePostReactionResponseDto(postReaction);
  }

  @Transactional
  public void deletePostReaction(Long postId, Long userId) {
    PostReaction postReaction = postReactionRepository.findByUserIdAndPostId(userId, postId)
                                                      .orElseThrow(() -> new CustomException(PostErrorCodeType.INVALID_POST_REACTION));
    postReactionService.deletePostReaction(postReaction);
  }
}

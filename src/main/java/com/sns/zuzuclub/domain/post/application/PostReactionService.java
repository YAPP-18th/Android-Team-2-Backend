package com.sns.zuzuclub.domain.post.application;

import com.sns.zuzuclub.domain.comment.dto.CreateCommentResponseDto;
import com.sns.zuzuclub.domain.notification.dto.FcmNotificationDto;
import com.sns.zuzuclub.domain.notification.model.PushNotification;
import com.sns.zuzuclub.domain.notification.repository.PushNotificationRepository;
import com.sns.zuzuclub.domain.post.model.PostReactionType;
import com.sns.zuzuclub.domain.post.dto.CreatePostReactionResponseDto;
import com.sns.zuzuclub.domain.post.dto.ReactionDto;
import com.sns.zuzuclub.domain.post.helper.PostHelper;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.model.PostReaction;
import com.sns.zuzuclub.domain.post.repository.PostReactionRepository;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.PostErrorCodeType;
import com.sns.zuzuclub.infra.fcm.FcmSender;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostReactionService {

  private final FcmSender fcmSender;

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final PostReactionRepository postReactionRepository;
  private final PushNotificationRepository pushNotificationRepository;

  @Transactional
  public CreatePostReactionResponseDto createPostReaction(Long postId, PostReactionType postReactionType, Long userId) {
    User user = UserHelper.findUserById(userRepository, userId);
    Post post = PostHelper.findPostById(postRepository, postId);

    PostReaction postReaction = new PostReaction(user, post, postReactionType);
    postReactionRepository.save(postReaction);

    if (userId.equals(post.getUser().getId())) {
      return new CreatePostReactionResponseDto(postReaction);
    }

    PushNotification pushNotification = postReaction.createPushNotification();
    pushNotificationRepository.save(pushNotification);

    FcmNotificationDto fcmNotificationDto = pushNotification.createFcmNotificationDto(post.getUser());
    fcmSender.sendMessage(fcmNotificationDto);

    return new CreatePostReactionResponseDto(postReaction);
  }

  public ReactionDto getPostReaction(Long postId) {
    Post post = PostHelper.findPostById(postRepository, postId);
    List<PostReaction> postReactionList = post.getPostReactionList();
    return new ReactionDto(postReactionList);
  }

  @Transactional
  public void deletePostReaction(Long postId, Long userId) {
    PostReaction postReaction = postReactionRepository.findByUserIdAndPostId(userId, postId)
                                                      .orElseThrow(() -> new CustomException(
                                                          PostErrorCodeType.INVALID_POST_REACTION));
    Post post = postReaction.getPost();
    post.decreasePostReactionCount();
    post.getPostReactionList().remove(postReaction);

    postReactionRepository.deleteById(postReaction.getId());
  }

}

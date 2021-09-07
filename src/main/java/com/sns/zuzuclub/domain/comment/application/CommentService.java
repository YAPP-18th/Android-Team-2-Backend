package com.sns.zuzuclub.domain.comment.application;

import com.sns.zuzuclub.domain.comment.dto.CreateCommentRequestDto;
import com.sns.zuzuclub.domain.comment.dto.CreateCommentResponseDto;
import com.sns.zuzuclub.domain.comment.helper.CommentHelper;
import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.comment.repository.CommentRepository;
import com.sns.zuzuclub.domain.notification.dto.FcmNotificationDto;
import com.sns.zuzuclub.domain.notification.model.PushNotification;
import com.sns.zuzuclub.domain.notification.repository.PushNotificationRepository;
import com.sns.zuzuclub.domain.post.helper.PostHelper;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;

import com.sns.zuzuclub.domain.user.service.UserService;
import com.sns.zuzuclub.infra.fcm.FcmSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

  private final FcmSender fcmSender;
  private final UserService userService;

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final PushNotificationRepository pushNotificationRepository;


  @Transactional
  public CreateCommentResponseDto createComment(Long userId, Long postId, CreateCommentRequestDto createCommentRequestDto) {

    userService.validateSuspension(userId);

    User userEntity = UserHelper.findUserById(userRepository, userId);
    Post postEntity = PostHelper.findPostById(postRepository, postId);

    Comment newComment = createCommentRequestDto.toEntity(commentRepository, userEntity,
        postEntity);
    commentRepository.save(newComment);
    if (userId.equals(postEntity.getUser().getId())) {
      return new CreateCommentResponseDto(newComment);
    }
    PushNotification pushNotification = newComment.createPushNotification();
    pushNotificationRepository.save(pushNotification);

    FcmNotificationDto fcmNotificationDto = pushNotification.createFcmNotificationDto(postEntity.getUser());
    fcmSender.sendMessage(fcmNotificationDto);

    return new CreateCommentResponseDto(newComment);
  }

  @Transactional
  public void deleteComment(Long commentId) {
    Comment comment = CommentHelper.findCommentById(commentRepository, commentId);
    comment.deleteContent();
  }
}

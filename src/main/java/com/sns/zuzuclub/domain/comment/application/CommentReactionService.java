package com.sns.zuzuclub.domain.comment.application;

import com.sns.zuzuclub.domain.comment.model.CommentReactionType;
import com.sns.zuzuclub.domain.comment.dto.CreateCommentReactionResponseDto;
import com.sns.zuzuclub.domain.comment.helper.CommentHelper;
import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.comment.model.CommentReaction;
import com.sns.zuzuclub.domain.comment.repository.CommentReactionRepository;
import com.sns.zuzuclub.domain.comment.repository.CommentRepository;
import com.sns.zuzuclub.domain.notification.dto.NotificationDto;
import com.sns.zuzuclub.domain.notification.model.PushNotification;
import com.sns.zuzuclub.domain.notification.repository.PushNotificationRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.CommentErrorCodeType;
import com.sns.zuzuclub.infra.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentReactionService {

  private final FcmService fcmService;

  private final UserRepository userRepository;
  private final CommentRepository commentRepository;
  private final CommentReactionRepository commentReactionRepository;
  private final PushNotificationRepository pushNotificationRepository;

  @Transactional
  public CreateCommentReactionResponseDto createCommentReaction(Long userId,
                                                                Long commentId,
                                                                CommentReactionType commentReactionType) {
    User user = UserHelper.findUserById(userRepository, userId);
    Comment comment = CommentHelper.findCommentById(commentRepository, commentId);

    CommentReaction commentReaction = new CommentReaction(user, comment, commentReactionType);

    PushNotification pushNotification = commentReaction.createPushNotification();
    pushNotificationRepository.save(pushNotification);

    NotificationDto notificationDto = pushNotification.createNotificationDto(comment.getUser());
    fcmService.sendMessage(notificationDto);


    return new CreateCommentReactionResponseDto(commentReactionRepository.save(commentReaction));
  }

  @Transactional
  public void deleteCommentReaction(Long userId, Long commentId) {
    CommentReaction commentReaction = commentReactionRepository.findByUserIdAndCommentId(userId, commentId)
                                                               .orElseThrow(() -> new CustomException(
                                                                       CommentErrorCodeType.INVALID_COMMENT_REACTION));

    Comment comment = commentReaction.getComment();
    comment.decreaseCommentReactionCount();
    comment.getCommentReactionList()
           .remove(commentReaction);

    commentReactionRepository.deleteById(commentReaction.getId());
  }

}

package com.sns.zuzuclub.domain.comment.application;

import com.sns.zuzuclub.constant.CommentReactionType;
import com.sns.zuzuclub.controller.comment.dto.CreateCommentReactionResponseDto;
import com.sns.zuzuclub.controller.comment.dto.CreateCommentRequestDto;
import com.sns.zuzuclub.controller.comment.dto.CreateCommentResponseDto;
import com.sns.zuzuclub.domain.comment.helper.CommentHelper;
import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.comment.model.CommentReaction;
import com.sns.zuzuclub.domain.comment.repository.CommentReactionRepository;
import com.sns.zuzuclub.domain.comment.repository.CommentRepository;
import com.sns.zuzuclub.domain.comment.service.CommentReactionService;
import com.sns.zuzuclub.domain.post.helper.PostHelper;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.CommentErrorCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

  private final CommentReactionService commentReactionService;

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final CommentRepository commentRepository;
  private final CommentReactionRepository commentReactionRepository;

  @Transactional
  public CreateCommentResponseDto createComment(Long userId, Long postId, CreateCommentRequestDto createCommentRequestDto) {
    User userEntity = UserHelper.findUserById(userRepository, userId);
    Post postEntity = PostHelper.findPostById(postRepository, postId);
    Comment newComment = createCommentRequestDto.toEntity(commentRepository, userEntity,
        postEntity);
    commentRepository.save(newComment);
    return new CreateCommentResponseDto(newComment);
  }

  @Transactional
  public CreateCommentReactionResponseDto createCommentReaction(Long userId, Long commentId, CommentReactionType commentReactionType) {
    User user = UserHelper.findUserById(userRepository, userId);
    Comment comment = CommentHelper.findCommentById(commentRepository, commentId);
    CommentReaction commentReaction = new CommentReaction(user, comment, commentReactionType);
    return new CreateCommentReactionResponseDto(commentReactionRepository.save(commentReaction));
  }

  @Transactional
  public void deleteCommentReaction(Long userId, Long commentId) {
    CommentReaction commentReaction = commentReactionRepository.findByUserIdAndCommentId(userId, commentId)
                                                               .orElseThrow(() -> new CustomException(CommentErrorCodeType.INVALID_COMMENT_REACTION));
    commentReactionService.deleteCommentReaction(commentReaction);
  }
}

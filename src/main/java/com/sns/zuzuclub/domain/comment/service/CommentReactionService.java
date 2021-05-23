package com.sns.zuzuclub.domain.comment.service;

import com.sns.zuzuclub.domain.comment.model.CommentReaction;
import com.sns.zuzuclub.domain.comment.repository.CommentReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentReactionService {

  private final CommentReactionRepository commentReactionRepository;

  public void deleteCommentReaction(CommentReaction commentReaction){
    commentReaction.deleteComment();
    commentReactionRepository.delete(commentReaction);
  }
}

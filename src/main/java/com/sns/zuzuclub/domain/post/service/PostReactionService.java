package com.sns.zuzuclub.domain.post.service;

import com.sns.zuzuclub.domain.post.model.PostReaction;
import com.sns.zuzuclub.domain.post.repository.PostReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReactionService {

  private final PostReactionRepository postReactionRepository;

  public void deletePostReaction(PostReaction postReaction){
    postReaction.deletePost();
    postReactionRepository.delete(postReaction);
  }

}

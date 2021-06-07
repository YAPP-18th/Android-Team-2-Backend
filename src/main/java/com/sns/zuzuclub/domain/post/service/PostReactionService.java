package com.sns.zuzuclub.domain.post.service;

import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.model.PostReaction;
import com.sns.zuzuclub.domain.post.repository.PostReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReactionService {

  private final PostReactionRepository postReactionRepository;

  public void deletePostReaction(PostReaction postReaction){

    Post post = postReaction.getPost();
    post.decreasePostReactionCount();
    post.getPostReactionList().remove(postReaction);

    postReactionRepository.deleteById(postReaction.getId());
  }

}

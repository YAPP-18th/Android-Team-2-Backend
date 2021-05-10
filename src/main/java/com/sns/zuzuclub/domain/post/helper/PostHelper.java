package com.sns.zuzuclub.domain.post.helper;

import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.PostErrorCodeType;

public class PostHelper {

  public static Post findPostById(PostRepository postRepository, Long postId){
    return postRepository.findById(postId).orElseThrow(()-> new CustomException(PostErrorCodeType.INVALID_POST_ID));
  }

}

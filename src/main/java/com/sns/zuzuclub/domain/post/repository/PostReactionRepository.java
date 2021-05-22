package com.sns.zuzuclub.domain.post.repository;

import com.sns.zuzuclub.domain.post.model.PostReaction;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReactionRepository extends JpaRepository<PostReaction, Long> {

  boolean existsByUserIdAndPostId(Long userId, Long postId);
  Optional<PostReaction> findByUserIdAndPostId(Long userId, Long postId);

}

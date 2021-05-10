package com.sns.zuzuclub.domain.post.repository;

import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
  List<Post> findByCreatedAtAfter(LocalDateTime createdAt, Pageable pageable);
  List<Post> findAllByUserIn(List<User> userList, Pageable pageable);
}

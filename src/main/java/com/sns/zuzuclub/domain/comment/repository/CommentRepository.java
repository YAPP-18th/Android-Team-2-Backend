package com.sns.zuzuclub.domain.comment.repository;

import com.sns.zuzuclub.domain.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}

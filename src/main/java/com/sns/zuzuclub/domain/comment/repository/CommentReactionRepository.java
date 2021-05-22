package com.sns.zuzuclub.domain.comment.repository;

import com.sns.zuzuclub.domain.comment.model.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {

}

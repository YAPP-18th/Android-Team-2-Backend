package com.sns.zuzuclub.domain.comment.model;

import com.sns.zuzuclub.domain.user.model.User;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sns.zuzuclub.domain.common.model.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class CommentReaction extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Comment comment;

  @Enumerated(EnumType.STRING)
  private CommentReactionType commentReactionType;

  public CommentReaction(User user, Comment comment, CommentReactionType commentReactionType) {
    this.user = user;
    this.commentReactionType = commentReactionType;
    initComment(comment);
  }

  public void initComment(Comment comment) {
    this.comment = comment;
    comment.getCommentReactionList().add(this);
    comment.increaseCommentReactionCount();
  }
}

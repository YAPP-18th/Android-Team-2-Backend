package com.sns.zuzuclub.domain.comment;

import com.sns.zuzuclub.constant.CommentReactionType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sns.zuzuclub.domain.AuditEntity;
import com.sns.zuzuclub.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class CommentReaction extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private User user;

  @ManyToOne
  private Comment comment;

  @Enumerated(EnumType.STRING)
  private CommentReactionType commentReactionType;
}

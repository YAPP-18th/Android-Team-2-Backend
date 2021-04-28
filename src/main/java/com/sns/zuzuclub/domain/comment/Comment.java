package com.sns.zuzuclub.domain.comment;

import com.sns.zuzuclub.domain.user.User;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sns.zuzuclub.domain.AuditEntity;
import com.sns.zuzuclub.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class Comment extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  private String content;

  @ManyToOne(fetch = FetchType.LAZY)
  private Comment parentComment;

  @OneToMany(mappedBy = "parentComment")
  private List<Comment> childComment = new ArrayList<>();

  @OneToMany(mappedBy = "comment")
  private List<CommentReaction> commentReaction = new ArrayList<>();

  private int commentReactionCount = 0;
}

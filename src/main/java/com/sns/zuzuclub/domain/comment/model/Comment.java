package com.sns.zuzuclub.domain.comment.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sns.zuzuclub.global.AuditEntity;
import com.sns.zuzuclub.domain.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private UserInfo user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JsonIgnore
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

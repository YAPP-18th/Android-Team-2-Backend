package com.sns.zuzuclub.domain.post.model;

import com.sns.zuzuclub.constant.PostReactionType;
import com.sns.zuzuclub.domain.user.model.User;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sns.zuzuclub.global.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class PostReaction extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @Enumerated(EnumType.STRING)
  private PostReactionType reactionType;

  public PostReaction(User user, Post post, PostReactionType postReactionType) {
    this();
    this.user = user;
    this.updatePost(post);
    this.reactionType = postReactionType;
  }

  public void updatePost(Post post) {
    if (this.post != null){
      this.post.decreasePostReactionCount();
      this.post.getPostReactionList().remove(this);
    }
    this.post = post;
    post.getPostReactionList().add(this);
    post.increasePostReactionCount();
  }

  public void deletePost(){
    this.post.decreasePostReactionCount();
    this.post.getPostReactionList().remove(this);
    this.post = null;
  }

  public boolean isOwnedBy(Long userId){
    return user.getId().equals(userId);
  }

}

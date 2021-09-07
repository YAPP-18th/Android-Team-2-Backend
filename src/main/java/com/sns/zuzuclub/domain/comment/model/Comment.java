package com.sns.zuzuclub.domain.comment.model;


import com.sns.zuzuclub.domain.notification.model.NotificationType;
import com.sns.zuzuclub.domain.notification.model.PushNotification;
import com.sns.zuzuclub.domain.user.model.User;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sns.zuzuclub.domain.common.model.AuditEntity;
import com.sns.zuzuclub.domain.post.model.Post;
import lombok.Builder;
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
  private List<Comment> childCommentList = new ArrayList<>();

  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentReaction> commentReactionList = new ArrayList<>();

  private int commentReactionCount = 0;

  @Builder
  public Comment(User user, Post post, String content, Comment parentComment) {
    this();
    this.initUser(user);
    this.initPost(post);
    this.content = content;
    this.updateParentComment(parentComment);
  }

  public void initUser(User user){
    this.user = user;
    user.getCommentList().add(this);
    user.increaseCommentCount();
  }

  public void initPost(Post post){
    if(this.post != null){
      this.post.decreaseCommentCount();
      this.post.getCommentList().remove(this);
    }
    this.post = post;
    post.getCommentList().add(this);
    post.increaseCommentCount();
  }

  public void updateParentComment(Comment parentComment){

    if(parentComment == null){
      return;
    }

    if (this.parentComment != null){
      this.parentComment.getChildCommentList().remove(this);
    }
    this.parentComment = parentComment;
    parentComment.getChildCommentList().add(this);
  }

  public Long getParentCommentId(){
    if(this.parentComment == null){
      return -1L;
    }
    return this.parentComment.getId();
  }

  public void increaseCommentReactionCount() { this.commentReactionCount += 1; }

  public void decreaseCommentReactionCount() {
    this.commentReactionCount -= 1;
  }

  public boolean hasUserCommentReaction(Long loginUserId){
    return this.getCommentReactionList()
               .stream()
               .anyMatch(commentReaction -> commentReaction.getUser()
                                                           .getId()
                                                           .equals(loginUserId));
  }

  public void deleteUser(){
    if (this.user != null){
      this.user.decreaseCommentCount();
      this.user.getCommentList().remove(this);
      this.user = null;
    }
  }

  public void deleteContent(){
    this.content = "삭제된 댓글 입니다.";
  }

  public PushNotification createPushNotification(){

    Long postWriterId = post.getUser().getId();
    Long targetId = post.getId();

    String message = user.getNickname() + " 님이 내 게시물에 댓글을 남겼습니다.";

    return PushNotification.builder()
                           .userId(postWriterId)
                           .senderId(user.getId())
                           .notificationType(NotificationType.COMMENT)
                           .redirectTargetId(targetId)
                           .alarmMessage(message)
                           .build();
  }
}

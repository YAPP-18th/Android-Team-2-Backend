package com.sns.zuzuclub.domain.comment.model;


import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
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

  @OneToMany(mappedBy = "comment")
  private List<CommentReaction> commentReactionList = new ArrayList<>();

  private int commentReactionCount = 0;

  @Builder
  public Comment(User user, Post post, String content, Comment parentComment) {
    this();
    this.updateUser(user);
    this.updatePost(post);
    this.content = content;
    this.updateParentComment(parentComment);
  }

  public void updateUser(User user){
    if (this.user != null){
      this.user.decreasCommentCount();
      this.user.getCommentList().remove(this);
    }
    this.user = user;
    user.getCommentList().add(this);
    user.increaseCommentCount();
  }

  public void updatePost(Post post){
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
      return null;
    }
    return this.parentComment.getId();
  }

  public UserInfo getWriterUserInfo(UserInfoRepository userInfoRepository){
    return UserHelper.findUserInfoById(userInfoRepository, this.user.getId());
  }
}
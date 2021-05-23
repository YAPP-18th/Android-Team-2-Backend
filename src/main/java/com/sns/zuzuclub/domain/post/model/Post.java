package com.sns.zuzuclub.domain.post.model;

import com.sns.zuzuclub.controller.post.dto.PostedStockDto;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import java.util.ArrayList;
import java.util.List;

import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sns.zuzuclub.constant.PostEmotionType;

import com.sns.zuzuclub.global.AuditEntity;
import com.sns.zuzuclub.domain.comment.model.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity
public class Post extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  private String content;

  @Enumerated(EnumType.STRING)
  private PostEmotionType postEmotionType;

  @OneToMany(mappedBy = "post")
  private List<PostedStock> postedStockList = new ArrayList<>();

  @OneToMany(mappedBy = "post")
  private List<Comment> commentList = new ArrayList<>();

  @OneToMany(mappedBy = "post")
  private List<PostReaction> postReactionList = new ArrayList<>();

  private String postImageUrl;
  private int commentCount = 0;
  private int postReactionCount = 0;

  @Builder
  public Post(User user, String content, PostEmotionType postEmotionType, String postImageUrl) {
    this();
    updateUser(user);
    this.content = content;
    this.postEmotionType = postEmotionType;
    this.postImageUrl = postImageUrl;
  }

  public void updateUser(User user){
    if(this.user != null){
      this.user.decreasePostCount();
      this.user.getPostList().remove(this);
    }
    this.user = user;
    user.getPostList().add(this);
    user.increasePostCount();
  }

  public UserInfo getWriterUserInfo(UserInfoRepository userInfoRepository){
    return UserHelper.findUserInfoById(userInfoRepository, getUser().getId());
  }

  public void increaseCommentCount(){
    this.commentCount += 1;
  }

  public void decreaseCommentCount(){
    this.commentCount -= 1;
  }

  public void increasePostReactionCount(){
    this.postReactionCount += 1;
  }

  public void decreasePostReactionCount(){
    this.postReactionCount -= 1;
  }

  public boolean hasUserPostReaction(Long userId){
    return this.postReactionList.stream()
                                .anyMatch(postReaction -> postReaction.getUser()
                                                                      .getId()
                                                                      .equals(userId));
  }
}

package com.sns.zuzuclub.domain.post.model;

import com.sns.zuzuclub.controller.post.dto.ModifyPostRequestDto;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.User;
import java.util.ArrayList;
import java.util.List;


import java.util.Optional;
import javax.persistence.CascadeType;
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

  @OneToMany(mappedBy = "post") // cascade 불가
  private List<PostedStock> postedStockList = new ArrayList<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> commentList = new ArrayList<>();

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostReaction> postReactionList = new ArrayList<>();

  private String postImageUrl;
  private int commentCount = 0;
  private int postReactionCount = 0;

  @Builder
  public Post(User user, String content, PostEmotionType postEmotionType, String postImageUrl) {
    initUser(user);
    this.content = content;
    this.postEmotionType = postEmotionType;
    this.postImageUrl = postImageUrl;
  }

  public void initUser(User user){
    this.user = user;
    user.getPostList().add(this);
    user.increasePostCount();
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

  public String getPostReactionByUser(Long loginUserId){
    Optional<PostReaction> result = this.postReactionList.stream()
                                                         .filter(postReaction -> postReaction.isOwnedBy(loginUserId))
                                                         .findFirst();
    if(result.isPresent()){
      return result.get().getReactionType().toString();
    }
    return "";
  }

  public void modify(ModifyPostRequestDto modifyPostRequestDto) {
    modifyContent(modifyPostRequestDto.getContent());
    this.postImageUrl = modifyPostRequestDto.getPostImageUrl();
    this.postEmotionType = modifyPostRequestDto.getPostEmotionType();
  }

  private void modifyContent(String newContent){
    if (newContent == null){
      this.content = "";
    }
    this.content = newContent;
  }

  public List<PostedStock> deletePostedStock(){
    this.postedStockList.forEach(oldPostedStock -> {
      deletePostEmotionInfo(oldPostedStock);
      oldPostedStock.deleteStock();
    });
    return this.postedStockList;
  }

  private void deletePostEmotionInfo(PostedStock oldPostedStock) {
    Stock oldStock = oldPostedStock.getStock();
    oldStock.removePostEmotionInfo(this.postEmotionType);
  }

  public void deleteUser(){
    if(this.user != null){
      this.user.decreasePostCount();
      this.user.getPostList().remove(this);
      this.user = null;
    }
  }

  public void deleteComment(){
    this.commentList.forEach(Comment::deleteUser);
  }
}

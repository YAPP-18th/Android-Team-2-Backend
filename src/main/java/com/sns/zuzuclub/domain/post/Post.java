package com.sns.zuzuclub.domain.post;

import com.sns.zuzuclub.domain.stock.StockPost;
import com.sns.zuzuclub.domain.user.UserHistory;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sns.zuzuclub.constant.PostEmotionType;

import com.sns.zuzuclub.domain.AuditEntity;
import com.sns.zuzuclub.domain.stock.Stock;
import com.sns.zuzuclub.domain.user.User;
import com.sns.zuzuclub.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Post extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private UserHistory userHistory;

  private String content;

  @Enumerated(EnumType.STRING)
  private PostEmotionType postEmotionType;

  @OneToMany(mappedBy = "post")
  private List<StockPost> stockPostList = new ArrayList<>();

  @OneToMany(mappedBy = "post")
  private List<Comment> commentList = new ArrayList<>();

  @OneToMany(mappedBy = "post")
  private List<PostReaction> postReactionList = new ArrayList<>();

  private String postImageUrl;
  private int commentCount = 0;
  private int postReactionCount = 0;
}

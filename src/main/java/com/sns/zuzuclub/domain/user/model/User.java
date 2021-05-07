package com.sns.zuzuclub.domain.user.model;

import com.sns.zuzuclub.domain.alarm.model.Notification;
import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.post.model.Post;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sns.zuzuclub.global.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @OneToOne(mappedBy = "user")
//  @Column(nullable = false)
//  private UserInfo userInfo;
//
//  @OneToOne(mappedBy = "user")
//  @Column(nullable = false)
//  private UserSecurity userSecurity;

  // 내가 팔로우 하는 사람
  @OneToMany(mappedBy = "fromUser")
  private Set<UserFollow> following = new HashSet<>();

  // 나를 팔로우 하는 사람
  @OneToMany(mappedBy = "toUser")
  private Set<UserFollow> followers = new HashSet<>();

  // 내가 차단 하는 사람
  @OneToMany(mappedBy = "fromUser")
  private Set<UserBlock> blocker = new HashSet<>();

  @OneToMany(mappedBy = "user")
  private List<Post> postList = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Comment> commentList = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Notification> notificationList = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<UserStockScrap> userStockScrapList = new ArrayList<>();

  private int postCount = 0;
  private int commentCount = 0;
  private int userStockScrapCount = 0;
  private int followerCount = 0;
  private int followingCount = 0;
}

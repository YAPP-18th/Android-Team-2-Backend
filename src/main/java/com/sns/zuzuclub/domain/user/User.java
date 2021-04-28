package com.sns.zuzuclub.domain.user;

import com.sns.zuzuclub.domain.alarm.Notification;
import com.sns.zuzuclub.domain.comment.Comment;
import com.sns.zuzuclub.domain.post.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.sns.zuzuclub.domain.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "user")
  private UserInfo userInfo;

  @OneToOne(mappedBy = "user")
  private UserSecurity userSecurity;

  // 내가 팔로우 하는 사람
  @OneToMany(mappedBy = "fromUser")
  private Set<UserFollow> following;

  // 나를 팔로우 하는 사람
  @OneToMany(mappedBy = "toUser")
  private Set<UserFollow> followers;

  // 내가 차단 하는 사람
  @OneToMany(mappedBy = "fromUser")
  private Set<UserBlock> blocker;

  @OneToMany(mappedBy = "user")
  private List<Post> postList = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Comment> commentList = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<Notification> notificationList = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  private List<UserStockScrap> userStockScrapList = new ArrayList<>();

  private int postCount;
  private int commentCount;
  private int userStockScrapCount;
  private int followerCount;
  private int followingCount;
}

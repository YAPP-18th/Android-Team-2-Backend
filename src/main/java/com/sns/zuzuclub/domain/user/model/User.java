package com.sns.zuzuclub.domain.user.model;

import com.sns.zuzuclub.domain.alarm.model.Notification;
import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.service.UserService;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.sns.zuzuclub.global.AuditEntity;
import javax.persistence.OrderBy;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
@Entity
public class User extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20)
  private String nickname = "";

  @Column(length = 360)
  private String introduction = "";

  private String profileImageUrl = ""; // 기본 값을 여기다가 초기화 해주면 좋을 것 같은데 흠

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
  @OrderBy
  // TODO 얘는 delete 할 때 영속성 전이하면, commnet 주인한테 문제 생김
  private List<Post> postList = new ArrayList<>();

  @OneToMany(mappedBy = "user")
  @OrderBy
  // TODO 얘는 delete 할 때 영속성 전이하면, post에서 잘못된 값 읽힘
  private List<Comment> commentList = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy
  private List<Notification> notificationList = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy
  private List<UserStockScrap> userStockScrapList = new ArrayList<>();

  private int postCount = 0;
  private int commentCount = 0;
  private int userStockScrapCount = 0;
  private int followerCount = 0;
  private int followingCount = 0;

  public void registerNickname(UserService userService,  String nickname){
    if(nickname.isEmpty()){
      throw new CustomException(UserErrorCodeType.EMPTY_NICKNAME);
    }
    userService.validateNickname(this.id, nickname);
    this.nickname = nickname;
  }

  public void registerIntroduction(String Introduction){
    this.introduction = Introduction;
  }

  public void registerProfileImageUrl(String profileImageUrl){
    this.profileImageUrl = profileImageUrl;
  }

  public void increaseUserStockScrapCount(){
    this.userStockScrapCount += 1;
  }

  public void decreaseUserStockScrapCount(){
    this.userStockScrapCount -= 1;
  }

  public void increasePostCount(){
    this.postCount += 1;
  }

  public void decreasePostCount(){
    this.postCount -= 1;
  }

  public void increaseCommentCount(){
    this.commentCount += 1;
  }

  public void decreaseCommentCount(){
    this.commentCount -= 1;
  }

  public void increaseFollowerCount(){
    this.followerCount += 1;
  }

  public void decreaseFollowerCount(){
    if (this.followerCount <= 0){
      return;
    }
    this.followerCount -= 1;
  }

  public void increaseFollowingCount(){
    this.followingCount += 1;
  }

  public void decreaseFollowingCount(){
    if (this.followingCount <= 0){
      return;
    }
    this.followingCount -= 1;
  }

  // 내가 팔로우하는 유저 리스트 리턴
  public List<User> getFollowingUserList(){
    return following.stream()
                    .map(UserFollow::getToUser)
                    .collect(Collectors.toList());
  }

  public boolean isScrapedStock(Stock stock){
    return userStockScrapList.stream()
                             .anyMatch(userStockScrap -> userStockScrap.getStock()
                                                                       .equals(stock));
  }

  public boolean hasFollowing(User targetUser){
    return following.stream()
                    .anyMatch(userFollow -> userFollow.isToUser(targetUser));
  }

  public boolean hasFollower(Long userId){
    return followers.stream()
                    .anyMatch(userFollow -> userFollow.isFromUserById(userId));
  }



}

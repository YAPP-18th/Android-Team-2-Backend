package com.sns.zuzuclub.domain.user.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sns.zuzuclub.global.AuditEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@EqualsAndHashCode(of="id")
@Entity
public class UserFollow extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

/*
  - fromUser 가 toUser를 팔로우함
   */

  @ManyToOne(fetch = FetchType.LAZY)
  private User fromUser;

  @ManyToOne(fetch = FetchType.LAZY)
  private User toUser;

  public UserFollow(User fromUser, User toUser) {
    initFromUser(fromUser);
    initToUser(toUser);
  }

  private void initFromUser(User fromUser){
    this.fromUser = fromUser;
    fromUser.getFollowing().add(this);
    fromUser.increaseFollowingCount();
  }

  private void initToUser(User toUser){
    this.toUser = toUser;
    toUser.getFollowers().add(this);
    toUser.increaseFollowerCount();
  }

  public void delete(){
    deleteFromUser();
    deleteToUser();
  }

  private void deleteFromUser() {
    this.fromUser.decreaseFollowingCount();
    this.fromUser.getFollowing().remove(this);
    this.fromUser = null;
  }

  private void deleteToUser() {
    this.toUser.decreaseFollowerCount();
    this.toUser.getFollowers().remove(this);
    this.toUser = null;
  }

  public boolean isToUser(User user){
    return toUser.getId().equals(user.getId());
  }

  public boolean isFromUserById(Long userId){
    return fromUser.getId().equals(userId);
  }
}

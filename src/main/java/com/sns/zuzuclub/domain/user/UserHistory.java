package com.sns.zuzuclub.domain.user;

import com.sns.zuzuclub.domain.alarm.Notification;
import com.sns.zuzuclub.domain.comment.Comment;
import com.sns.zuzuclub.domain.post.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sns.zuzuclub.domain.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class UserHistory extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne(mappedBy = "userHistory")
  private User user;

  @OneToMany(mappedBy = "userHistory")
  private List<Post> postList = new ArrayList<>();

  @OneToMany(mappedBy = "userHistory")
  private List<Comment> commentList = new ArrayList<>();

  @OneToMany(mappedBy = "userHistory")
  private List<Notification> notificationList = new ArrayList<>();

  @OneToMany(mappedBy = "userHistory")
  private List<UserStockScrap> userStockScrapList = new ArrayList<>();

  private int postCount;
  private int commentCount;
  private int userStockScrapCount;
}

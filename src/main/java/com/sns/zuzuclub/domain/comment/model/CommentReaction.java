package com.sns.zuzuclub.domain.comment.model;

import com.sns.zuzuclub.domain.notification.model.NotificationType;
import com.sns.zuzuclub.domain.notification.model.PushNotification;
import com.sns.zuzuclub.domain.user.model.User;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sns.zuzuclub.domain.common.model.AuditEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class CommentReaction extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  private Comment comment;

  @Enumerated(EnumType.STRING)
  private CommentReactionType commentReactionType;

  public CommentReaction(User user, Comment comment, CommentReactionType commentReactionType) {
    this.user = user;
    this.commentReactionType = commentReactionType;
    initComment(comment);
  }

  public void initComment(Comment comment) {
    this.comment = comment;
    comment.getCommentReactionList().add(this);
    comment.increaseCommentReactionCount();
  }

  public PushNotification createPushNotification() {
    Long userId = this.comment.getUser().getId();
    Long targetId = this.comment.getPost().getId();

    String message = this.user.getNickname() + " 님이 내 댓글에 \""+commentReactionType.getContent()+"\" 반응했습니다.";

    return PushNotification.builder()
                           .userId(userId)
                           .senderId(user.getId())
                           .notificationType(NotificationType.COMMENT_REACTION)
                           .redirectTargetId(targetId)
                           .alarmMessage(message)
                           .build();
  }
}

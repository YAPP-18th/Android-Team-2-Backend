package com.sns.zuzuclub.domain.notification.model;

import com.sns.zuzuclub.domain.comment.model.CommentReactionType;
import com.sns.zuzuclub.domain.common.model.AuditEntity;
import com.sns.zuzuclub.domain.notification.dto.FcmNotificationDto;
import com.sns.zuzuclub.domain.post.model.PostReactionType;
import com.sns.zuzuclub.domain.user.model.User;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PushNotification extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long userId;

  private Long senderId;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  private Long redirectTargetId;

  private String alarmMessage;

  private boolean isRead;

  @Builder
  public PushNotification(Long userId,
                          Long senderId,
                          NotificationType notificationType,
                          Long redirectTargetId,
                          String alarmMessage) {
    this.userId = userId;
    this.senderId = senderId;
    this.notificationType = notificationType;
    this.redirectTargetId = redirectTargetId;
    this.alarmMessage = alarmMessage;
    this.isRead = false;
  }

  public FcmNotificationDto createFcmNotificationDto(User targetUser){
    Map<String, String> data = new HashMap<>();
    data.put("redirectId", redirectTargetId.toString());
    data.put("reactionType", parseReactionType().toString());
    return FcmNotificationDto.builder()
                             .title(notificationType.getTitle()) // "팔로우 알림"
                             .body(alarmMessage) //""
                             .fcmToken(targetUser.getFcmToken())
                             .data(data)
                             .build();
  }

  public void updateReadStatusTrue(){
    this.isRead = true;
  }

  public Long parseReactionType(){
    boolean isPostReaction = this.notificationType.equals(NotificationType.POST_REACTION);
    if (isPostReaction){
      String reactionContent = parseReactionContent();
      PostReactionType postReactionType = getPostReactionType(reactionContent);
      return (long) postReactionType.ordinal();
    }
    boolean isCommentReaction = this.notificationType.equals(NotificationType.COMMENT_REACTION);
    if (isCommentReaction){
      String reactionContent = parseReactionContent();
      CommentReactionType commentReactionType = getCommentReactionType(reactionContent);
      return (long)commentReactionType.ordinal();
    }
    return -1L;
  }

  private String parseReactionContent() {
    int start = alarmMessage.indexOf("\"");
    int end = alarmMessage.lastIndexOf("\"");
    return alarmMessage.substring(start+1, end);
  }

  private CommentReactionType getCommentReactionType(String reactionContent) {
    return Arrays.stream(CommentReactionType.values())
                 .filter(c -> c.getContent().equals(reactionContent))
                 .findFirst()
                 .get();
  }


  private PostReactionType getPostReactionType(String reactionContent) {
    return Arrays.stream(PostReactionType.values())
                 .filter(p -> p.getContent()
                               .equals(reactionContent))
                 .findFirst()
                 .get();
  }
}

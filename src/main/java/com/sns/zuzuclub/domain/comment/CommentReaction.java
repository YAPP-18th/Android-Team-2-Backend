package com.sns.zuzuclub.domain.comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sns.zuzuclub.domain.AuditEntity;
import com.sns.zuzuclub.domain.user.User;
import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "comment_reaction")
@Getter
public class CommentReaction extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_reaction_id")
    private Long commentReactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public CommentReaction(Long commentReactionId, User user, Comment comment) {
        this.commentReactionId = commentReactionId;
        this.user = user;
        this.comment = comment;
    }
}

package com.sns.zuzuclub.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "comment")
@Getter
public class Comment extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "comment_content")
    private String content;

    @OneToMany(mappedBy = "comment")
    @Column(name = "comment_reaction")
    private List<CommentReaction> commentReaction;

    @ManyToOne
    @JoinColumn(name = "parent")
    private Comment childrenComment;

    @OneToMany(mappedBy = "parent")
    private List<Comment> parent;

    private Integer commentReactionCount;

    @Builder
    public Comment(Long commentId, User user, Post post, String content) {
        this.commentId = commentId;
        this.user = user;
        this.post = post;
        this.content = content;
    }
}

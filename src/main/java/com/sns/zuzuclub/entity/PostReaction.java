package com.sns.zuzuclub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sns.zuzuclub.constant.PostReacionType;

import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "post_reaction")
@Getter
public class PostReaction extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_reaction_id")
    private Long postReactionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "reaction_type")
    private PostReacionType reactionType;

    @Builder
    public PostReaction(Long postReactionId, User user, Post post, PostReacionType reactionType) {
        this.postReactionId = postReactionId;
        this.user = user;
        this.post = post;
        this.reactionType = reactionType;
    }    

}

package com.sns.zuzuclub.domain.post;

import java.util.ArrayList;
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

import com.sns.zuzuclub.constant.PostEmotion;

import com.sns.zuzuclub.domain.AuditEntity;
import com.sns.zuzuclub.domain.stock.Stock;
import com.sns.zuzuclub.domain.user.User;
import com.sns.zuzuclub.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "post")
@Getter
public class Post extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "post_content", nullable = false)
    private String content;

    @Column(name = "post_emotion", nullable = false)
    private PostEmotion postEmotion;

    @OneToMany(mappedBy = "post")
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<PostReaction> postReaction = new ArrayList<>();;

    @OneToMany
    @Column(nullable = true)
    private List<Stock> stock = new ArrayList<>();
    
    private Integer commentCount;
    private Integer postReactionCount;

    @Builder
    public Post(Long postId, User user, String content, PostEmotion postEmotion) {
        this.postId = postId;
        this.user = user;
        this.content = content;
        this.postEmotion = postEmotion;
    }

    


}

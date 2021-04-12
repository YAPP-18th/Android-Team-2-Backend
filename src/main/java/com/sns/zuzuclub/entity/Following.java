package com.sns.zuzuclub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "following")
@Getter
public class Following extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long followingId;

    @ManyToOne
	@JoinColumn(name = "user")
    private User user;

    @ManyToOne
	@JoinColumn(name = "target_user")
	private User targetUser;

    @Builder
    public Following(Long followingId, User user, User targetUser) {
        this.followingId = followingId;
        this.user = user;
        this.targetUser = targetUser;
    }
    
}

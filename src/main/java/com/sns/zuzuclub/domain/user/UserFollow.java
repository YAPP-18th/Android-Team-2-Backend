package com.sns.zuzuclub.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sns.zuzuclub.domain.AuditEntity;
import lombok.Builder;
import lombok.Getter;


@Entity
@Table(name = "following")
@Getter
public class UserFollow extends AuditEntity {

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
    public UserFollow(Long followingId, User user, User targetUser) {
        this.followingId = followingId;
        this.user = user;
        this.targetUser = targetUser;
    }
    
}

package com.sns.zuzuclub.domain.user;

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
@Table(name = "blockfriend")
@Getter
public class UserBlock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blockfriend_id")
    private Long blockFriend;

    @ManyToOne
	@JoinColumn(name = "user")
    private User user;

    @ManyToOne
	@JoinColumn(name = "target_ser")
	private User targetUser;

    @Builder
    public UserBlock(Long blockFriend, User user, User targetUser) {
        this.blockFriend = blockFriend;
        this.user = user;
        this.targetUser = targetUser;
    }

}

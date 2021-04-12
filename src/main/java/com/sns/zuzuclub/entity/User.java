package com.sns.zuzuclub.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "user")
@Getter
public class User extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "nickname", nullable = false, length = 20)
    private String nickName;

    @Column(name = "introduction", nullable = true, length = 360)
    private String introduction;

    @Column(name = "profileimage", nullable = true)
    private String profileImage;

    @Builder
    public User(Long userId, String nickName, String introduction, String profileImage) {
        this.userId = userId;
        this.nickName = nickName;
        this.introduction = introduction;
        this.profileImage = profileImage;
    }
}

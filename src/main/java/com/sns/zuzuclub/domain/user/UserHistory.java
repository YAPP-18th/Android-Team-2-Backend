package com.sns.zuzuclub.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.sns.zuzuclub.domain.AuditEntity;
import lombok.Getter;

@Entity
@Table(name = "user_history")
@Getter
public class UserHistory extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_history_id")
    private Long userHistoryId;

    @OneToOne
    private User user;

}

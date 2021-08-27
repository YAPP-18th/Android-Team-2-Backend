package com.sns.zuzuclub.domain.user.model;

import com.sns.zuzuclub.domain.common.model.AuditEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Report extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long targetUserId;

    public Report(Long userId, User targetUser) {
        this.userId = userId;
        this.targetUserId = targetUser.getId();
        targetUser.increaseReportCount();
    }
}

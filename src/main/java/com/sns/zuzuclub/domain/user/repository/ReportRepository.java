package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.user.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findTop1ByTargetUserIdOrderByCreatedAtDesc(Long targetUserId);

    boolean existsByUserIdAndTargetUserId(Long userId, Long targetUserId);
}

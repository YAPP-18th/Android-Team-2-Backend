package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.user.model.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findAllByTargetUserId(Long targetUserId);
}

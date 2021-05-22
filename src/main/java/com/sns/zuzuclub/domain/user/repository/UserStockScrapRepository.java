package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStockScrapRepository extends JpaRepository<UserStockScrap, Long>{
  List<UserStockScrap> findTop6ByUser_Id(Long userId);
}

package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStockScrapRepository extends JpaRepository<UserStockScrap, Long>{
  List<UserStockScrap> findAllByUser_Id(Long userId);
  List<UserStockScrap> findTop6ByUser_Id(Long userId);
  Optional<UserStockScrap> findByUserIdAndStockId(Long userId, Long stockId);
}

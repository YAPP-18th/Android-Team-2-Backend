package com.sns.zuzuclub.domain.user.repository;

import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStockScrapRepository extends JpaRepository<UserStockScrap, Long>{
  List<UserStockScrap> findAllByUser_Id(Long userId);
  List<UserStockScrap> findTop6ByUser_Id(Long userId);
  boolean existsByUserAndStock(User user, Stock stock);
}

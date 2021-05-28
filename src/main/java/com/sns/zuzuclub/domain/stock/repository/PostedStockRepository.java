package com.sns.zuzuclub.domain.stock.repository;

import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostedStockRepository extends JpaRepository<PostedStock, Long> {
  List<PostedStock> findAllByCreatedAtAfter(LocalDateTime from);
  List<PostedStock> findAllByStock(Stock stock);
}

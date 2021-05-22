package com.sns.zuzuclub.domain.stock.repository;

import com.sns.zuzuclub.domain.stock.model.Stock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

  List<Stock> findAllByStockNameContaining(String stockName);

}

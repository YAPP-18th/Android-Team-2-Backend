package com.sns.zuzuclub.domain.stock.repository;

import com.sns.zuzuclub.domain.stock.model.Stock;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
  Optional<Stock> findByStockName(String stockName);
  List<Stock> findAllByStockNameIn(List<String> stockNameList);
  List<Stock> findAllByStockNameStartsWith(String stockName);
  List<Stock> findAllByTotalCountIsGreaterThan(int totalCount);

}

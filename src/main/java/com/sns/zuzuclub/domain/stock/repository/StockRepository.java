package com.sns.zuzuclub.domain.stock.repository;

import com.sns.zuzuclub.domain.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

}

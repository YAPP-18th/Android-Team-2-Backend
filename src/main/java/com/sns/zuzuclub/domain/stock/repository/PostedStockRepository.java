package com.sns.zuzuclub.domain.stock.repository;

import com.sns.zuzuclub.domain.stock.model.PostedStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostedStockRepository extends JpaRepository<PostedStock, Long> {

}

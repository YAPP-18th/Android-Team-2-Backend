package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostedStockDto {

  private Long postedStockId;
  private String postedStockName;

  @Builder
  public PostedStockDto(PostedStock postedStock) {
    Stock stock = postedStock.getStock();
    postedStockId = stock.getId();
    postedStockName = stock.getStockName();
  }
}

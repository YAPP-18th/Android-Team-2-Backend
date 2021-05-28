package com.sns.zuzuclub.domain.stock.helper;

import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.StockErrorCodeType;

public class StockHelper {

  public static Stock findStockById(StockRepository stockRepository, Long stockId){
    return stockRepository.findById(stockId)
                          .orElseThrow(() -> new CustomException(StockErrorCodeType.INVALID_STOCK_ID));
  }

}

package com.sns.zuzuclub.domain.stock.application;

import com.sns.zuzuclub.domain.user.dto.signup.StockResponseDto;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchStockService {

  private final StockRepository stockRepository;

  public List<StockResponseDto> searchStockByStockName(String stockName) {
    List<Stock> stockList = stockRepository.findAllByStockNameStartsWith(stockName);
    return StockResponseDto.toListFrom(stockList);
  }
}

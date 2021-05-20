package com.sns.zuzuclub.domain.stock.application;

import com.sns.zuzuclub.controller.signup.dto.SearchStockResponseDto;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchStockService {

  private final StockRepository stockRepository;

  public List<SearchStockResponseDto> searchStockByStockName(String stockName) {
    List<Stock> stockList = stockRepository.findAllByStockNameContaining(stockName);
    return SearchStockResponseDto.toListFrom(stockList);
  }
}

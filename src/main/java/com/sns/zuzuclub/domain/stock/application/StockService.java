package com.sns.zuzuclub.domain.stock.application;

import com.sns.zuzuclub.controller.stock.dto.StockResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockService {

  public StockResponseDto getStock(Long userId, Long stockId) {
    return null;
  }

  @Transactional
  public void createScrapStock(Long userId, Long stockId) {
  }

  @Transactional
  public void deleteScrapStock(Long userId, Long stockId) {
  }
}

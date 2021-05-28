package com.sns.zuzuclub.domain.stock.application;

import com.sns.zuzuclub.controller.stock.dto.StockResponseDto;
import com.sns.zuzuclub.domain.stock.helper.StockHelper;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockService {

  private final StockRepository stockRepository;
  private final UserRepository userRepository;

  public StockResponseDto getStock(Long userId, Long stockId) {
    User user = UserHelper.findUserById(userRepository, userId);
    Stock stock = StockHelper.findStockById(stockRepository, stockId);
    return new StockResponseDto(user, stock);
  }

  @Transactional
  public void createScrapStock(Long userId, Long stockId) {
  }

  @Transactional
  public void deleteScrapStock(Long userId, Long stockId) {
  }
}

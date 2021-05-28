package com.sns.zuzuclub.domain.stock.application;

import com.sns.zuzuclub.controller.stock.dto.StockResponseDto;
import com.sns.zuzuclub.domain.stock.helper.StockHelper;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.domain.user.repository.UserStockScrapRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.StockErrorCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockService {

  private final StockRepository stockRepository;
  private final UserRepository userRepository;
  private final UserStockScrapRepository userStockScrapRepository;

  public StockResponseDto getStock(Long userId, Long stockId) {
    User user = UserHelper.findUserById(userRepository, userId);
    Stock stock = StockHelper.findStockById(stockRepository, stockId);
    return new StockResponseDto(user, stock);
  }

  @Transactional
  public void createUserStockScrap(Long userId, Long stockId) {
    User user = UserHelper.findUserById(userRepository, userId);
    Stock stock = StockHelper.findStockById(stockRepository, stockId);
    new UserStockScrap(user, stock);
    // 영속성 전이
  }

  @Transactional
  public void deleteUserStockScrap(Long userId, Long stockId) {
    UserStockScrap userStockScrap = userStockScrapRepository.findByUserIdAndStockId(userId, stockId)
                                                            .orElseThrow(() -> new CustomException(StockErrorCodeType.INVALID_STOCK_ID));
    userStockScrap.deleteUser();
    userStockScrap.deleteStock();
    userStockScrapRepository.delete(userStockScrap);
  }
}

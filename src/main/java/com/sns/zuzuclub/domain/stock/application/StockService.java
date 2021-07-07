package com.sns.zuzuclub.domain.stock.application;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.controller.stock.dto.StockResponseDto;
import com.sns.zuzuclub.domain.stock.helper.StockHelper;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.domain.user.repository.UserStockScrapRepository;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
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
    List<UserStockScrap> userStockScrapList = userStockScrapRepository.findAllByUserIdAndStockId(userId, stockId);
//                                                            .orElseThrow(() -> new CustomException(StockErrorCodeType.INVALID_STOCK_ID));
    userStockScrapList.forEach(userStockScrap -> {
      userStockScrap.deleteUser();
      userStockScrap.deleteStock();
    });
    userStockScrapRepository.deleteAll(userStockScrapList);
  }

  public Map<PostEmotionType, Stock> getHotStockMap(List<Stock> stockList) {
    Map<PostEmotionType, Stock> resultMap = new EnumMap<>(PostEmotionType.class);
    for (PostEmotionType postEmotionType : PostEmotionType.values()) {
      Stock maxCountStock = getMaxEmotionRatioStock(stockList, postEmotionType);
      resultMap.put(postEmotionType, maxCountStock);
    }
    return resultMap;
  }

  private Stock getMaxEmotionRatioStock(List<Stock> stockList, PostEmotionType postEmotionType) {
    Stock maxCountStock = stockList.stream()
                                   .filter(stock -> stock.isSameEmotion(postEmotionType))
                                   .max(Comparator.comparing(Stock::getStockEmotionRatio))
                                   // 카카오로 기본값
                                   .orElseGet(() -> StockHelper.findStockById(stockRepository, 1876L));
    return maxCountStock;
  }
}

package com.sns.zuzuclub.domain.stock.application;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.controller.stock.dto.StockResponseDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.service.PostService;
import com.sns.zuzuclub.domain.stock.helper.StockHelper;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.PostedStockRepository;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.domain.user.repository.UserStockScrapRepository;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.StockErrorCodeType;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StockService {

  private final PostService postService;
  private final StockRepository stockRepository;
  private final UserRepository userRepository;
  private final PostedStockRepository postedStockRepository;
  private final UserStockScrapRepository userStockScrapRepository;

  public StockResponseDto getStock(Long userId, Long stockId) {

    User user = UserHelper.findUserById(userRepository, userId);
    Stock stock = StockHelper.findStockById(stockRepository, stockId);

    // TODO 일단 전체 다 계산했는데, 배치로 돌려야함
    List<PostedStock> postedStockList = postedStockRepository.findAllByStock(stock);

    if (postedStockList.isEmpty()) {
      return StockResponseDto.builder()
                             .user(user)
                             .stock(stock)
                             .postEmotionType(PostEmotionType.UP)
                             .postEmotionValue(0)
                             .size(postedStockList.size())
                             .build();
    }

    List<Post> postList = postedStockList.stream()
                                         .map(PostedStock::getPost)
                                         .collect(Collectors.toList());
    Entry<PostEmotionType, Integer> mostPostedPostEmotionType = postService.getMostPostedPostEmotionType(postList);

    return StockResponseDto.builder()
                           .user(user)
                           .stock(stock)
                           .postEmotionType(mostPostedPostEmotionType.getKey())
                           .postEmotionValue(mostPostedPostEmotionType.getValue())
                           .size(postedStockList.size())
                           .build();
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

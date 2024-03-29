package com.sns.zuzuclub.domain.common.application;

import com.sns.zuzuclub.domain.post.model.PostEmotionType;
import com.sns.zuzuclub.domain.common.dto.HomeResponseDto;
import com.sns.zuzuclub.domain.common.dto.UserScrapDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.post.service.PostCalculateService;
import com.sns.zuzuclub.domain.stock.application.StockService;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import com.sns.zuzuclub.domain.user.repository.UserRepository;
import com.sns.zuzuclub.domain.user.repository.UserStockScrapRepository;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class HomeService {

  private final PostCalculateService postCalculateService;
  private final StockService stockService;

  private final UserRepository userRepository;
  private final PostRepository postRepository;
  private final StockRepository stockRepository;
  private final UserStockScrapRepository userStockScrapRepository;

  public HomeResponseDto getHomeInfo(Long userId) {

    List<Post> postList = postRepository.findTop100ByOrderByCreatedAtDesc();
    Entry<PostEmotionType, Integer> weather = postCalculateService.getMostPostedPostEmotionType(postList);
    int postCount = postList.size();

    List<Stock> stockList = stockRepository.findAllByTotalCountIsGreaterThan(5);
    Map<PostEmotionType, Stock> hotStockMap = stockService.getHotStockMap(stockList);

    List<UserStockScrap> userStockScrapList = userStockScrapRepository.findTop6ByUser_Id(userId);

    return HomeResponseDto.builder()
                          .weather(weather)
                          .postCount(postCount)
                          .hotStockMap(hotStockMap)
                          .userStockScrapList(userStockScrapList)
                          .build();
  }

  public UserScrapDto getUserStockScrap(Long userId) {
    User user = UserHelper.findUserById(userRepository, userId);
    List<UserStockScrap> userStockScrapList = userStockScrapRepository.findAllByUser_Id(userId);
    return new UserScrapDto(user, userStockScrapList);
  }
}

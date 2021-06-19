package com.sns.zuzuclub.domain.home.application;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.controller.home.dto.HomeResponseDto;
import com.sns.zuzuclub.controller.home.dto.UserStockScrapDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.post.service.PostService;
import com.sns.zuzuclub.domain.stock.application.StockService;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.StockRepository;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import com.sns.zuzuclub.domain.user.repository.UserStockScrapRepository;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class HomeInfoService {

  private final PostService postService;
  private final StockService stockService;

  private final PostRepository postRepository;
  private final StockRepository stockRepository;
  private final UserStockScrapRepository userStockScrapRepository;

  public HomeResponseDto getHomeInfo(Long userId) {

    List<Post> postList = postRepository.findTop100ByOrderByCreatedAtDesc();
    Entry<PostEmotionType, Integer> weather = postService.getMostPostedPostEmotionType(postList);
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

  public List<UserStockScrapDto> getUserStockScrap(Long userId) {
    List<UserStockScrap> userStockScrapList = userStockScrapRepository.findAllByUser_Id(userId);
    return UserStockScrapDto.toListFrom(userStockScrapList);
  }
}

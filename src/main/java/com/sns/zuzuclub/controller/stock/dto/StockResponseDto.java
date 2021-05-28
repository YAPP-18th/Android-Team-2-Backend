package com.sns.zuzuclub.controller.stock.dto;

import com.sns.zuzuclub.controller.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import com.sns.zuzuclub.domain.user.repository.UserStockScrapRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class StockResponseDto {

  private Long stockId;
  private String stockName;
  private String todayEmotion;
  private int todayEmotionValue;
  private boolean isScraped;
  private List<PostResponseDto> postResponseDtoList;

  public StockResponseDto(User user, Stock stock, UserInfoRepository userInfoRepository) {
    this.stockId = stock.getId();
    this.stockName = stock.getStockName();
    this.todayEmotion = stock.getStockName();
    this.todayEmotionValue = stock.getTodayEmotionValue();
    this.isScraped = user.isScrapedStock(stock);

    List<Post> postList = stock.getPostList();
    this.postResponseDtoList = PostResponseDto.toListFrom(userInfoRepository, postList, user.getId());
  }
}

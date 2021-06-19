package com.sns.zuzuclub.controller.stock.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.controller.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.User;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class StockResponseDto {

  private Long stockId;
  private String stockName;
  private PostEmotionType todayEmotion;
  private float todayEmotionValue;
  private boolean isScraped;
  private List<PostResponseDto> postResponseDtoList;

  public StockResponseDto(User user, Stock stock) {
    this.stockId = stock.getId();
    this.stockName = stock.getStockName();
    this.todayEmotion = stock.getStockEmotionType();
    this.todayEmotionValue = stock.getStockEmotionRatio();
    this.isScraped = user.isScrapedStock(stock);

    List<Post> postList = stock.getPostList();
    this.postResponseDtoList = PostResponseDto.ListFrom(postList, user.getId());
  }
}

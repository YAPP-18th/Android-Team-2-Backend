package com.sns.zuzuclub.domain.stock.dto;

import com.sns.zuzuclub.domain.post.model.PostEmotionType;
import com.sns.zuzuclub.domain.post.dto.PostResponseDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.User;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class StockResponseDto {

  private Long stockId;
  private String stockName;
  private PostEmotionType todayEmotion;
  private int todayEmotionValue;
  private boolean isScraped;
  private List<PostResponseDto> postResponseDtoList;

  public StockResponseDto(User user, Stock stock) {
    this.stockId = stock.getId();
    this.stockName = stock.getStockName();
    this.todayEmotion = stock.getStockEmotionType();
    this.todayEmotionValue = (int)(stock.getStockEmotionRatio() * 100);
    this.isScraped = user.isScrapedStock(stock);

    List<Post> postList = stock.getPostList()
                               .stream()
                               .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                               .collect(Collectors.toList());
    this.postResponseDtoList = PostResponseDto.ListFrom(postList, user.getId());
  }
}

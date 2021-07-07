package com.sns.zuzuclub.domain.common.dto;

import com.sns.zuzuclub.domain.post.model.PostEmotionType;

import com.sns.zuzuclub.domain.stock.model.Stock;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class HotStockDto {
  private Long stockId;
  private String stockName;
  private PostEmotionType postEmotionType;

  public HotStockDto(Entry<PostEmotionType, Stock> hotStockEntry) {
    Stock stock = hotStockEntry.getValue();

    this.postEmotionType = hotStockEntry.getKey();
    this.stockId = stock.getId();
    this.stockName = stock.getStockName();
  }

  public static List<HotStockDto> toListFrom(Map<PostEmotionType, Stock> hotStockMap) {
    return hotStockMap.entrySet()
        .stream()
        .map(HotStockDto::new)
        .collect(Collectors.toList());
  }
}

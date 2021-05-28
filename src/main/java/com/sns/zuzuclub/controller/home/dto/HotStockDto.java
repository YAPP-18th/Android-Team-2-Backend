package com.sns.zuzuclub.controller.home.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.homeInfo.model.HotStock;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class HotStockDto {
  private Long stockId;
  private String stockName;
  private PostEmotionType postEmotionType;
  private int rank;

  public HotStockDto(HotStock hotStock) {
    this.stockId = hotStock.getStock().getId();
    this.stockName = hotStock.getStock().getStockName();
    this.postEmotionType = hotStock.getPostEmotionType();
    this.rank = hotStock.getRanking();
  }

  public static List<HotStockDto> toListFrom(List<HotStock> hotStockList) {
    return hotStockList.stream()
                       .sorted(Comparator.comparing(HotStock::getRanking))
                       .map(HotStockDto::new)
                       .collect(Collectors.toList());
  }
}

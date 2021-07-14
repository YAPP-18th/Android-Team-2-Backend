package com.sns.zuzuclub.domain.common.dto;

import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class UserStockScrapDto {

  @ApiModelProperty(value = "주식 종목 고유값", example = "1")
  private Long stockId;

  @ApiModelProperty(value = "주식 종목 명", example = "삼성전자")
  private String stockName;

  @ApiModelProperty(value = "주식 대표 감정", example = "UP, DOWN, EXPECT, UNSTABLE")
  private String stockEmotionType;

  public UserStockScrapDto(UserStockScrap userStockScrap) {
    Stock stock = userStockScrap.getStock();
    this.stockId = stock.getId();
    this.stockName = stock.getStockName();
    this.stockEmotionType = stock.getStockEmotionType().toString();
  }

  public static List<UserStockScrapDto> toListFrom(List<UserStockScrap> userStockScrapList) {
    return userStockScrapList.stream()
                      .map(UserStockScrapDto::new)
                      .collect(Collectors.toList());
  }
}

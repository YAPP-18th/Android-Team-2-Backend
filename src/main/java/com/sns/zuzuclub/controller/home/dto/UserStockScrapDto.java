package com.sns.zuzuclub.controller.home.dto;

import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.UserStockScrap;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class UserStockScrapDto {

  @ApiModelProperty(value = "주식 종목 고유값", example = "1")
  private Long stockId;

  @ApiModelProperty(value = "주식 종목 명", example = "삼성전자")
  private String stockName;

  public UserStockScrapDto(UserStockScrap userStockScrap) {
    Stock stock = userStockScrap.getStock();
    this.stockId = stock.getId();
    this.stockName = stock.getStockName();
  }

  public static List<UserStockScrapDto> toListFrom(List<UserStockScrap> userStockScrapList) {
    return userStockScrapList.stream()
                      .map(UserStockScrapDto::new)
                      .collect(Collectors.toList());
  }
}

package com.sns.zuzuclub.controller.signup.dto;

import com.sns.zuzuclub.domain.stock.model.Stock;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class SearchStockResponseDto {

  @ApiModelProperty(value = "주식 종목 고유값", example = "1")
  private Long stockId;

  @ApiModelProperty(value = "주식 종목 명", example = "삼성전자")
  private String stockName;

  public SearchStockResponseDto(Stock stock) {
    this.stockId = stock.getId();
    this.stockName = stock.getStockName();
  }

  public static List<SearchStockResponseDto> toListFrom(List<Stock> stockList) {
    return stockList.stream()
                    .map(SearchStockResponseDto::new)
                    .collect(Collectors.toList());
  }
}

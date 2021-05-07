package com.sns.zuzuclub.controller.signup.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class StockListResponseDto {

  @ApiModelProperty(value = "주식 종목 고유값", example = "1")
  private String stockId;

  @ApiModelProperty(value = "주식 종목 명", example = "삼성전자")
  private String stockName;
}

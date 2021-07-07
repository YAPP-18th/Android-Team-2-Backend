package com.sns.zuzuclub.domain.post.dto;

import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PostedStockDto {

  @ApiModelProperty(value = "언급한 주식 id", example = "1")
  private Long postedStockId;

  @ApiModelProperty(value = "언급한 주식 이름", example = "삼성전자")
  private String postedStockName;

  @Builder
  public PostedStockDto(PostedStock postedStock) {
    Stock stock = postedStock.getStock();
    postedStockId = stock.getId();
    postedStockName = stock.getStockName();
  }

  public static List<PostedStockDto> listOf(Post post){
    return post.getPostedStockList()
               .stream()
               .map(PostedStockDto::new)
               .collect(Collectors.toList());
  }
}

package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.user.model.User;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;


@Getter
public class CreatePostRequestDto {

  @ApiModelProperty(value = "게시글 내용", example = "내용내용내용")
  private String content;

  @ApiModelProperty(value = "게시글 감정", example = "UP")
  private PostEmotionType postEmotionType;

  @ApiModelProperty(value = "언급한 주식들의 id 값")
  private List<Long> requestStockIdList;

  public Post toPostEntity(User user){
    return Post.builder()
               .user(user)
               .content(this.content)
               .postEmotionType(this.postEmotionType)
               .build();
  }
}

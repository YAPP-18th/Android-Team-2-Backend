package com.sns.zuzuclub.domain.post.dto;

import com.sns.zuzuclub.domain.post.model.PostEmotionType;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ModifyPostRequestDto {

  @ApiModelProperty(value = "수정할 게시글 내용", example = "내용내용내용")
  private String content;

  @ApiModelProperty(value = "수정할 게시글 감정", example = "UP")
  private PostEmotionType postEmotionType;

  @ApiModelProperty(value = "수정할 언급 주식 이름들")
  private List<String> postedStockNameList;

  @ApiModelProperty(value = "수정할 게시글 사진 url", example = "www.사진")
  private String postImageUrl;
}

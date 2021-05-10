package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePostResponseDto {

  @ApiModelProperty(value = "게시글id", example = "1")
  private Long postId;

  @ApiModelProperty(value = "게시글 내용", example = "내용내용내용")
  private String content;

  @ApiModelProperty(value = "게시글 감정", example = "UP")
  private PostEmotionType postEmotionType;

  @ApiModelProperty(value = "언급한 주식들 id/이름")
  private List<PostedStockDto> postedStockDtoList;

  @ApiModelProperty(value = "게시글 사진url", example = "www.사진. -> 아직 해야함...!")
  private String postImageUrl;

  @Builder
  public CreatePostResponseDto(Post post){
    this.postId = post.getId();
    this.content = post.getContent();
    this.postEmotionType = post.getPostEmotionType();
    this.postedStockDtoList = PostedStockDto.toListFrom(post);
    this.postImageUrl = post.getPostImageUrl();
  }
}

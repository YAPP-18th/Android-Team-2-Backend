package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.util.S3Uploader;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Getter
public class CreatePostRequestDto {

  @ApiModelProperty(value = "게시글 image URL", example = "www.naver.com")
  private String postImageUrl;

  @ApiModelProperty(value = "게시글 내용", example = "내용내용내용")
  private String content;

  @ApiModelProperty(value = "게시글 감정", example = "UP")
  private PostEmotionType postEmotionType;

  @ApiModelProperty(value = "언급한 주식들의 종목명")
  private List<String> requestStockNameList = new ArrayList<>();

  public Post toPostEntity(User user){
    return Post.builder()
               .user(user)
               .content(content)
               .postEmotionType(postEmotionType)
               .postImageUrl(postImageUrl)
               .build();
  }
}

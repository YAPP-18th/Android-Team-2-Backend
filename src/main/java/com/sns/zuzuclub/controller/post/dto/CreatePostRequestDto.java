package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.util.S3Uploader;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Setter
@Getter
public class CreatePostRequestDto {

  @ApiModelProperty(value = "게시글 내용", example = "내용내용내용")
  private String content;

  @ApiModelProperty(value = "게시글 감정", example = "UP")
  private PostEmotionType postEmotionType;

  @ApiModelProperty(value = "언급한 주식들의 id 값")
  private List<Long> requestStockIdList;

  public Post toPostEntity(User user, S3Uploader s3Uploader, MultipartFile multipartFile){

    String postImageUrl = null;
    if (multipartFile != null){
      postImageUrl = s3Uploader.upload(user.getId(), multipartFile);
    }

    return Post.builder()
               .user(user)
               .content(content)
               .postEmotionType(postEmotionType)
               .postImageUrl(postImageUrl)
               .build();
  }
}

package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import java.util.List;
import lombok.Getter;


@Getter
public class CreatePostRequestDto {
  private PostEmotionType postEmotionType;
  private String content;
  private List<Long> postedStockIdList;
  //private MultipartFile multipartFile; // 오브젝트 스토리지 제공받은 후에 구현

  public Post toPostEntity(User user){
    return Post.builder()
               .user(user)
               .content(content)
               .build();
  }
}

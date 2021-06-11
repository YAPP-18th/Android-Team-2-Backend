package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.domain.post.model.Post;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Page;

@ToString
@Getter
public class FeedResponseDto {

  private List<PostResponseDto> postResponseDtoList;
  private int listCount;
  private int currentPage;
  private int totalPage;
  private boolean isLastPage;


  public FeedResponseDto(Page<Post> postPage, Long loginUserId) {
    List<Post> postList= postPage.getContent();
    this.postResponseDtoList = PostResponseDto.ListFrom(postList, loginUserId);
    this.listCount = postResponseDtoList.size();
    this.currentPage = postPage.getNumber();
    this.totalPage = postPage.getTotalPages() - 1;
    this.isLastPage = postPage.isLast();
  }
}

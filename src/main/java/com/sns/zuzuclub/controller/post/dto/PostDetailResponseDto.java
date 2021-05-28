package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.controller.comment.dto.CommentResponseDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class PostDetailResponseDto {

  @ApiModelProperty(value = "게시글id", example = "")
  private Long postId;

  @ApiModelProperty(value = "작성자 프사", example = "")
  private String profileImageUrl;

  @ApiModelProperty(value = "작성자 닉네임", example = "")
  private String nickname;

  @ApiModelProperty(value = "게시글 감정", example = "")
  private PostEmotionType postEmotionType;

  @ApiModelProperty(value = "게시글 사진url", example = "")
  private String postImageUrl;

  @ApiModelProperty(value = "언급한 주식들", example = "")
  private List<PostedStockDto> postedStockDtoList;

  @ApiModelProperty(value = "게시글 내용", example = "")
  private String content;

  @ApiModelProperty(value = "게시글 반응 수", example = "")
  private int postReactionCount;

  @ApiModelProperty(value = "게시글 댓글 수", example = "")
  private int commentCount;

  @ApiModelProperty(value = "댓글들", example = "")
  private List<CommentResponseDto> commentResponseDtoList;

  @ApiModelProperty(value = "내 게시물인지 여부", example = "")
  private boolean isMine;

  @ApiModelProperty(value = "반응했는지 여부", example = "")
  private boolean hasUserPostReaction;


  public PostDetailResponseDto(Post post, Long loginUserId) {

    User writer = post.getUser();

    this.isMine = loginUserId.equals(writer.getId());
    this.hasUserPostReaction = post.hasUserPostReaction(loginUserId);

    this.profileImageUrl = writer.getProfileImageUrl();
    this.nickname = writer.getNickname();

    this.postId = post.getId();
    this.postEmotionType = post.getPostEmotionType();
    this.postImageUrl = post.getPostImageUrl();
    this.postedStockDtoList = PostedStockDto.listOf(post);
    this.content = post.getContent();
    this.postReactionCount = post.getPostReactionCount();
    this.commentCount = post.getCommentCount();
    this.commentResponseDtoList = CommentResponseDto.toListFrom(post, loginUserId);
  }
}

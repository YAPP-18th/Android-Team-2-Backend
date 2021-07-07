package com.sns.zuzuclub.domain.post.dto;

import com.sns.zuzuclub.domain.post.model.PostEmotionType;
import com.sns.zuzuclub.domain.comment.dto.CommentResponseDto;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.util.TimeConvertor;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
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

  @ApiModelProperty(value = "내가 누른 반응, 없으면 빈 문자열 \"\" ", example = "")
  private String postReaction;

  @ApiModelProperty(value = "시간", example = "")
  private String createdAt;

  @ApiModelProperty(value = "로그인 유저가 작성자를 팔로우하는지 여부", example = "")
  private boolean isFollowdByLoginUser;

  public PostDetailResponseDto(Post post, Long loginUserId) {

    User writer = post.getUser();

    this.isMine = loginUserId.equals(writer.getId());
    this.isFollowdByLoginUser = writer.hasFollower(loginUserId);
    this.postReaction = post.getPostReactionByUser(loginUserId);

    this.profileImageUrl = writer.getProfileImageUrl();
    this.nickname = writer.getNickname();

    this.postId = post.getId();
    this.postEmotionType = post.getPostEmotionType();
    this.postImageUrl = post.getPostImageUrl();
    this.postedStockDtoList = PostedStockDto.listOf(post);
    this.content = post.getContent();
    this.postReactionCount = post.getPostReactionCount();
    this.createdAt = TimeConvertor.convertToString(post.getCreatedAt());
    this.commentCount = post.getCommentCount();
    this.commentResponseDtoList = CommentResponseDto.toListFrom(post, loginUserId);
  }
}

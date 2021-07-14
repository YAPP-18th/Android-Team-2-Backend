package com.sns.zuzuclub.domain.post.dto;

import com.sns.zuzuclub.domain.post.model.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.util.TimeConvertor;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class PostResponseDto {

    // 유저관련
    @ApiModelProperty(value = "작성자id", example = "1")
    private Long userId;

    @ApiModelProperty(value = "작성자 프로필 사진 url", example = "")
    private String profileImageUrl;

    @ApiModelProperty(value = "작성자 닉네임", example = "")
    private String nickname;

    // post 관련
    @ApiModelProperty(value = "게시글id", example = "")
    private Long postId;

    @ApiModelProperty(value = "게시글 내용", example = "")
    private String content;

    @ApiModelProperty(value = "게시글 감정", example = "UP")
    private PostEmotionType postEmotionType;

    @ApiModelProperty(value = "게시글 사진url", example = "")
    private String postImageUrl;

    @ApiModelProperty(value = "댓글 수", example = "")
    private int commentCount;

    @ApiModelProperty(value = "게시글 반응 수", example = "")
    private int postReactionCount;

    // post에 매핑된 주식 종목 관련
    @ApiModelProperty(value = "언급한 주식들 id/이름", example = "")
    List<PostedStockDto> postedStockDtoList;

    @ApiModelProperty(value = "내 게시물인지 여부", example = "")
    private boolean isUserPost;

    @ApiModelProperty(value = "작성일", example = "")
    private String createdAt;

    @ApiModelProperty(value = "로그인 유저의 게시글 반응", example = "")
    private String postReaction;

    @ApiModelProperty(value = "로그인 유저가 작성자를 팔로우하는지 여부", example = "")
    private boolean isFollowdByLoginUser;

    public PostResponseDto(Post post, Long loginUserId) {

        User writer = post.getUser();

        this.isUserPost = loginUserId.equals(writer.getId());
        this.isFollowdByLoginUser = writer.hasFollower(loginUserId);
        this.userId = writer.getId();
        this.profileImageUrl = writer.getProfileImageUrl();
        this.nickname = writer.getNickname();

        this.postId = post.getId();
        this.content = post.getContent();
        this.postEmotionType = post.getPostEmotionType();
        this.postImageUrl = post.getPostImageUrl();
        this.commentCount = post.getCommentCount();
        this.postReaction = post.getPostReactionByUser(loginUserId);
        this.postReactionCount = post.getPostReactionCount();
        this.createdAt = TimeConvertor.convertToString(post.getCreatedAt());

        this.postedStockDtoList = PostedStockDto.listOf(post);
    }

    public static List<PostResponseDto> ListFrom(List<Post> postList, Long loginUserId){

        if (postList.isEmpty()) {
            return new ArrayList<>();
        }
        return postList.stream()
                       .map(post -> new PostResponseDto(post, loginUserId))
                       .collect(Collectors.toList());
    }
}

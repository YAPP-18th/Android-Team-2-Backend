package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.helper.UserHelper;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

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
    private boolean isMine = false;

    public PostResponseDto(UserInfoRepository userInfoRepository, Post post, Long userId) {

        UserInfo writerUserInfo = post.getWriterUserInfo(userInfoRepository);

        this.isMine = userId.equals(writerUserInfo.getId());
        this.userId = writerUserInfo.getId();
        this.profileImageUrl = writerUserInfo.getProfileImageUrl();
        this.nickname = writerUserInfo.getNickname();

        this.postId = post.getId();
        this.content = post.getContent();
        this.postEmotionType = post.getPostEmotionType();
        this.postImageUrl = post.getPostImageUrl();
        this.commentCount = post.getCommentCount();
        this.postReactionCount = post.getPostReactionCount();

        this.postedStockDtoList = PostedStockDto.toListFrom(post);
    }

    public static List<PostResponseDto> toListFrom(UserInfoRepository userInfoRepository, List<Post> postList, Long userId){
        return postList.stream()
                       .map(post -> new PostResponseDto(userInfoRepository, post, userId))
                       .collect(Collectors.toList());
    }
}

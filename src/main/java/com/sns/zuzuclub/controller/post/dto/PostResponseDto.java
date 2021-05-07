package com.sns.zuzuclub.controller.post.dto;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PostResponseDto {

    // 유저관련
    private Long userId;
    private String profileImageUrl;
    private String nickname;

    // post 관련
    private Long postId;
    private String content;
    private PostEmotionType postEmotionType;
    private String postImageUrl;
    private int commentCount;
    private int postReactionCount;

    // post에 매핑된 주식 종목 관련
    List<PostedStockDto> postedStockDtoList;

    public PostResponseDto(UserInfo userInfo, Post post) {
        this.userId = userInfo.getId();
        this.profileImageUrl = userInfo.getProfileImageUrl();
        this.nickname = userInfo.getNickname();
        this.postId = post.getId();
        this.content = post.getContent();
        this.postEmotionType = post.getPostEmotionType();
        this.postImageUrl = post.getPostImageUrl();
        this.commentCount = post.getCommentCount();
        this.postReactionCount = post.getPostReactionCount();
        this.postedStockDtoList = post.getPostedStockList()
                                      .stream()
                                      .map(PostedStockDto::new)
                                      .collect(Collectors.toList());
    }
}

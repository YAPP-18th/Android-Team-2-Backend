package com.sns.zuzuclub.controller.comment.dto;

import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import com.sns.zuzuclub.domain.user.repository.UserInfoRepository;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    @ApiModelProperty(value = "댓글id", example = "")
    private Long commentId;

    @ApiModelProperty(value = "작성자 닉네임", example = "")
    private String nickname;

    @ApiModelProperty(value = "작성자 프사", example = "")
    private String profileImageUrl;

    @ApiModelProperty(value = "댓글 내용", example = "")
    private String content;

    @ApiModelProperty(value = "부모 댓글id", example = "")
    private Long parentCommentId;

    @ApiModelProperty(value = "작성일(수정필요)", example = "")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "댓글 반응 수", example = "")
    private int commentReactionCount;

    @ApiModelProperty(value = "내가 댓글 반응 작성했는지", example = "")
    private boolean hasUserCommentReaction;


    public CommentResponseDto(UserInfoRepository userInfoRepository, Comment comment, Long userId) {
        UserInfo writerUserInfo = comment.getWriterUserInfo(userInfoRepository);
        this.commentId = comment.getId();
        this.nickname = writerUserInfo.getNickname();
        this.profileImageUrl = writerUserInfo.getProfileImageUrl();
        this.content = comment.getContent();
        this.parentCommentId = comment.getParentCommentId();
        this.createdAt = comment.getCreatedAt();
        this.commentReactionCount = comment.getCommentReactionCount();
        this.hasUserCommentReaction = comment.hasUserCommentReaction(userId);
    }

    public static List<CommentResponseDto> toListFrom(UserInfoRepository userInfoRepository, Post post, Long userId){
        return post.getCommentList()
                   .stream()
                   .map(comment -> new CommentResponseDto(userInfoRepository, comment, userId))
                   .collect(Collectors.toList());
    }
}

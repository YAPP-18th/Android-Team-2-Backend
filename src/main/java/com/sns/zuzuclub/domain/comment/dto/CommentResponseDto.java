package com.sns.zuzuclub.domain.comment.dto;

import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.global.util.TimeConvertor;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class CommentResponseDto {
    @ApiModelProperty(value = "댓글id", example = "")
    private Long commentId;

    @ApiModelProperty(value = "댓글 작성자 id", example = "")
    private Long commentWriterId;

    @ApiModelProperty(value = "작성자 닉네임", example = "")
    private String nickname;

    @ApiModelProperty(value = "작성자 프사", example = "")
    private String profileImageUrl;

    @ApiModelProperty(value = "댓글 내용", example = "")
    private String content;

    @ApiModelProperty(value = "부모 댓글id", example = "")
    private Long parentCommentId;

    @ApiModelProperty(value = "작성일", example = "")
    private String createdAt;

    @ApiModelProperty(value = "댓글 반응 수", example = "")
    private int commentReactionCount;

    @ApiModelProperty(value = "내가 댓글 반응 작성했는지", example = "")
    private boolean hasUserCommentReaction;

    @ApiModelProperty(value = "내가 쓴 댓글인지", example = "")
    private boolean isMine;

    public CommentResponseDto(Comment comment, Long loginUserId) {
        User writer = comment.getUser();
        this.commentWriterId = writer.getId();
        this.commentId = comment.getId();
        this.nickname = writer.getNickname();
        this.profileImageUrl = writer.getProfileImageUrl();
        this.content = comment.getContent();
        this.parentCommentId = comment.getParentCommentId();
        this.createdAt = TimeConvertor.convertToString(comment.getCreatedAt());
        this.commentReactionCount = comment.getCommentReactionCount();
        this.hasUserCommentReaction = comment.hasUserCommentReaction(loginUserId);
        this.isMine = comment.getUser().getId().equals(loginUserId);
    }

    public static List<CommentResponseDto> toListFrom(Post post, Long loginUserId){
        return post.getCommentList()
                   .stream()
                   .map(comment -> new CommentResponseDto(comment, loginUserId))
                   .collect(Collectors.toList());
    }
}

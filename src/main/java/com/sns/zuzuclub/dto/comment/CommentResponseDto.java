package com.sns.zuzuclub.dto.comment;

import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.comment.model.CommentReaction;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.user.model.User;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentResponseDto {

    private UserInfo user;
    private Post post;
    private String content;
    private Comment parentComment;
    private List<Comment> childComment = new ArrayList<>();
    private List<CommentReaction> commentReaction = new ArrayList<>();
    private int commentReactionCount = 0;

}

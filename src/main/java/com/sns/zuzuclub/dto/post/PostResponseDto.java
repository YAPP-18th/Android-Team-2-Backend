package com.sns.zuzuclub.dto.post;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.comment.model.Comment;
import com.sns.zuzuclub.domain.post.model.PostReaction;
import com.sns.zuzuclub.domain.stock.model.StockPost;
import com.sns.zuzuclub.domain.user.model.UserInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {

    private Long id;
    private UserInfo user;
    private String content;
    private PostEmotionType postEmotionType;
    private List<StockPost> stockPostList = new ArrayList<>();
    private List<Comment> commentList = new ArrayList<>();
    private List<PostReaction> postReactionList = new ArrayList<>();
    private String postImageUrl;
    private int commentCount = 0;
    private int postReactionCount = 0;

}

package com.sns.zuzuclub.dto.post;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.stock.model.Stock;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostRequestDto {

    private Long userId;
    private String content;
    private PostEmotionType postEmotionType;
    private List<String> stocks = new ArrayList<>();
    private String postImageUrl;

}

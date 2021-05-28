package com.sns.zuzuclub.domain.post.service;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

  public Entry<PostEmotionType, Integer> getMostPostedPostEmotionType(List<Post> postList) {

    Map<PostEmotionType, Integer> postEmotionTypeIntegerMap = getPostEmotionTypeWithPostedCount(postList);

    return postEmotionTypeIntegerMap.entrySet()
                                    .stream()
                                    .max(Entry.comparingByValue())
                                    .get();
  }

  public Map<PostEmotionType, Integer> getPostEmotionTypeWithPostedCount(List<Post> postList) {

    Map<PostEmotionType, Integer> postEmotionTypeWithCountMap = PostEmotionType.getPostEmotionTypeWithCountMap();

    postList.forEach(post -> {
      PostEmotionType postEmotionType = post.getPostEmotionType();
      if (postEmotionType != null) {
        postEmotionTypeWithCountMap.compute(postEmotionType, (emotion, count) -> count++);
      }
    });

    return postEmotionTypeWithCountMap;
  }
}

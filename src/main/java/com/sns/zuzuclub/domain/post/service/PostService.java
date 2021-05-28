package com.sns.zuzuclub.domain.post.service;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.global.exception.SchedulerException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

  public Entry<PostEmotionType, Integer> getMostPostedPostEmotionType(List<Post> postList) {

    Map<PostEmotionType, Integer> postEmotionTypeWithPostedCount = getPostEmotionTypeWithPostedCount(postList);

    return postEmotionTypeWithPostedCount.entrySet()
                                         .stream()
                                         .max(Entry.comparingByValue())
                                         .orElseThrow(()->new SchedulerException("가장 많이 포스팅 된 감정 계산 에러"));
  }

  public Map<PostEmotionType, Integer> getPostEmotionTypeWithPostedCount(List<Post> postList) {

    Map<PostEmotionType, Integer> postEmotionTypeWithCountMap = PostEmotionType.initPostEmotionTypeWithCountMap();

    postList.forEach(post -> {
      PostEmotionType postEmotionType = post.getPostEmotionType();
      if (postEmotionType != null) {
        postEmotionTypeWithCountMap.compute(postEmotionType, (emotion, count) -> count++);
      }
    });

    return postEmotionTypeWithCountMap;
  }
}

package com.sns.zuzuclub.domain.post.service;

import com.sns.zuzuclub.domain.post.model.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.global.exception.SchedulerException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostCalculateService {

  public Entry<PostEmotionType, Integer> getMostPostedPostEmotionType(List<Post> postList) {

    Map<PostEmotionType, Integer> postEmotionTypeWithPostedCount = getPostEmotionTypeWithPostedCount(postList);
    return calculateMostPostedPostEmotionType(postEmotionTypeWithPostedCount);

  }

  private Map<PostEmotionType, Integer> getPostEmotionTypeWithPostedCount(List<Post> postList) {

    Map<PostEmotionType, Integer> postEmotionTypeWithCountMap = PostEmotionType.initPostEmotionTypeWithCountMap();
    calculatePostedCount(postList, postEmotionTypeWithCountMap);
    return postEmotionTypeWithCountMap;
  }

  private void calculatePostedCount(List<Post> postList,
                                    Map<PostEmotionType, Integer> postEmotionTypeWithCountMap) {
    postList.forEach(post -> {
      PostEmotionType postEmotionType = post.getPostEmotionType();
      if (postEmotionType != null) {
        postEmotionTypeWithCountMap.compute(postEmotionType, (emotion, count) -> count += 1);
      }
    });
  }

  private Entry<PostEmotionType, Integer> calculateMostPostedPostEmotionType(
      Map<PostEmotionType, Integer> postEmotionTypeWithPostedCount) {
    return postEmotionTypeWithPostedCount.entrySet()
                                         .stream()
                                         .max(Entry.comparingByValue())
                                         .orElseThrow(
                                             () -> new SchedulerException("가장 많이 포스팅 된 감정 계산 에러"));
  }
}

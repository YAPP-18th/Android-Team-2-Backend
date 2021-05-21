package com.sns.zuzuclub.domain.homeInfo.application;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.homeInfo.model.Weather;
import com.sns.zuzuclub.domain.homeInfo.repository.WeatherRepository;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.repository.PostRepository;
import com.sns.zuzuclub.domain.post.service.PostService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeWeatherService {

  private final PostService postService;

  private final PostRepository postRepository;
  private final WeatherRepository weatherRepository;

  public void createWeather(LocalDateTime from){

    // from 이후로 작성된 모든 포스트를 가져온다.
    List<Post> postList = postRepository.findAllByCreatedAtAfter(from);
    if (postList.isEmpty()) {
      return;
    }
    Entry<PostEmotionType, Integer> postEmotionTypeIntegerEntry = postService.getMaxCountPostEmotionTypeEntry(postList);
    Weather weather = new Weather(postEmotionTypeIntegerEntry, postList.size());
    weatherRepository.save(weather);
  }
}

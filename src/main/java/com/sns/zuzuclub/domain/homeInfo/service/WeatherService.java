package com.sns.zuzuclub.domain.homeInfo.service;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.homeInfo.model.Weather;
import com.sns.zuzuclub.domain.homeInfo.repository.WeatherRepository;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.post.service.PostService;
import java.util.List;
import java.util.Map.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class WeatherService {

  private final PostService postService;
  private final WeatherRepository weatherRepository;

  public void createWeather(List<Post> postList){
    Entry<PostEmotionType, Integer> postEmotionTypeIntegerEntry = postService.getMostPostedPostEmotionType(postList);
    Weather weather = new Weather(postEmotionTypeIntegerEntry, postList.size());
    weatherRepository.save(weather);
  }
}

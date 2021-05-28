package com.sns.zuzuclub.domain.homeInfo.model;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.global.AuditEntity;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Weather extends AuditEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private PostEmotionType postEmotionType;

  private float percentage;

  public Weather(Entry<PostEmotionType, Integer> postEmotionTypeIntegerEntry, int listSize){

    PostEmotionType maxPostEmotionType = postEmotionTypeIntegerEntry.getKey();
    float percentage = (float) postEmotionTypeIntegerEntry.getValue() / listSize * 100;

    this.postEmotionType = maxPostEmotionType;
    this.percentage = percentage;
  }
}

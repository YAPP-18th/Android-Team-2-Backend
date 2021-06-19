package com.sns.zuzuclub.domain.stock.model;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.global.exception.CustomException;
import com.sns.zuzuclub.global.exception.errorCodeType.StockErrorCodeType;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Stock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String stockName;

  @Enumerated(EnumType.STRING)
  private PostEmotionType stockEmotionType = PostEmotionType.UP;

  private int upCount = 0;

  private int downCount = 0;

  private int expectCount = 0;

  private int unstableCount = 0;

  private int totalCount = 0;

  @OneToMany(mappedBy = "stock")
  private List<PostedStock> postedStockList = new ArrayList<>();

  private void increaseTotalCount(){
    this.totalCount += 1;
  }

  private void increaseUpCount(){
    this.upCount += 1;
  }

  private void decreaseUpCount(){
    this.upCount -= 1;
  }

  private void increaseDownCount(){
    this.downCount += 1;
  }

  private void decreaseDownCount(){
    this.downCount -= 1;
  }

  private void increaseExpectCount(){
    this.expectCount += 1;
  }

  private void decreaseExpectCount(){
    this.expectCount -= 1;
  }

  private void increaseUnstableCount(){
    this.unstableCount += 1;
  }

  private void decreaseUnstableCount(){
    this.unstableCount -= 1;
  }

  private void increaseStockEmotionCount(PostEmotionType postEmotionType) {
    switch (postEmotionType) {
      case UP:
        increaseUpCount();
        break;
      case DOWN:
        increaseDownCount();
        break;
      case EXPECT:
        increaseExpectCount();
        break;
      case UNSTABLE:
        increaseUnstableCount();
        break;
    }
  }

  private void calculateStockEmotionType(){
    Map<PostEmotionType, Integer> map = new EnumMap<>(PostEmotionType.class);
    map.put(PostEmotionType.UP, upCount);
    map.put(PostEmotionType.DOWN, downCount);
    map.put(PostEmotionType.EXPECT, expectCount);
    map.put(PostEmotionType.UNSTABLE, unstableCount);

    Entry<PostEmotionType, Integer> entry = map.entrySet()
                                               .stream()
                                               .max(Entry.comparingByValue())
                                               .orElseThrow(() -> new CustomException(StockErrorCodeType.STOCK_EMOTION_UPDATE_ERROR));
    this.stockEmotionType = entry.getKey();
  }

  public boolean isSameEmotion(PostEmotionType postEmotionType){
    return this.stockEmotionType.equals(postEmotionType);
  }

  public float getStockEmotionRatio(){
    if(totalCount == 0){
      return 0;
    }
    float stockEmotionValue = 0;
    switch (this.stockEmotionType){
      case UP:
        stockEmotionValue = (float) this.upCount;
        break;
      case DOWN:
        stockEmotionValue = (float) this.downCount;
        break;
      case EXPECT:
        stockEmotionValue = (float) this.expectCount;
        break;
      case UNSTABLE:
        stockEmotionValue = (float) this.unstableCount;
        break;
    }
    return stockEmotionValue / totalCount;
  }

  public void updatePostEmotionInfo(PostEmotionType postEmotionType) {
    increaseTotalCount();
    if (postEmotionType == null) {
      return;
    }
    increaseStockEmotionCount(postEmotionType);
    calculateStockEmotionType();
  }

  public List<Post> getPostList(){
    return this.postedStockList.stream()
                               .map(PostedStock::getPost)
                               .collect(Collectors.toList());
  }
}

package com.sns.zuzuclub.domain.homeInfo.model;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.stock.model.Stock;
import java.util.List;
import java.util.Map.Entry;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class HotStock {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Stock stock;

  @Enumerated(EnumType.STRING)
  private PostEmotionType postEmotionType;

  private int ranking;

  public void updateStock(List<Entry<Stock, Integer>> newHotStockList){
    this.stock = newHotStockList.get(this.ranking - 1).getKey();
  }
}

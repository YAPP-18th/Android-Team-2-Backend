package com.sns.zuzuclub.domain.stock.service;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.post.model.Post;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.repository.PostedStockRepository;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostedStockService {

  public List<Entry<Stock, Integer>> getNewHotStockListByPostEmotionType(List<PostedStock> postedStockList, PostEmotionType postEmotionType) {
    Map<Stock, Integer> stockWithPostedCountMap = getStockWithPostedCountByPostEmotionType(postedStockList, postEmotionType);
    return stockWithPostedCountMap.entrySet()
                                  .stream()
                                  .sorted(Collections.reverseOrder(Entry.comparingByValue()))
                                  .limit(10)
                                  .collect(Collectors.toList());
  }

  private Map<Stock, Integer> getStockWithPostedCountByPostEmotionType(List<PostedStock> postedStockList, PostEmotionType postEmotionType) {
    Map<Stock, Integer> stockWithCountMap = new HashMap<>();
    postedStockList.stream()
                   .filter(postedStock -> postEmotionType.equals(postedStock.getPost().getPostEmotionType()))
                   .forEachOrdered(postedStock -> {
                     Stock stock = postedStock.getStock();
                     stockWithCountMap.putIfAbsent(stock, 0);
                     stockWithCountMap.compute(stock, (key, value) -> value++);
                   });
    return stockWithCountMap;
  }
}

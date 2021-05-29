package com.sns.zuzuclub.domain.homeInfo.service;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.homeInfo.model.HotStock;
import com.sns.zuzuclub.domain.homeInfo.repository.HotStockRepository;
import com.sns.zuzuclub.domain.stock.model.PostedStock;
import com.sns.zuzuclub.domain.stock.model.Stock;
import com.sns.zuzuclub.domain.stock.service.PostedStockService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class HotStockService {

  private final PostedStockService postedStockService;
  private final HotStockRepository hotStockRepository;

  public void updateHotStockRank(List<PostedStock> postedStockList) {

    for (PostEmotionType postEmotionType : PostEmotionType.values()) {
      List<HotStock> oldHotStockList = hotStockRepository.findAllByPostEmotionType(postEmotionType);
      List<Map.Entry<Stock, Integer>> newHotStockList = postedStockService.getNewHotStockListByPostEmotionType(postedStockList, postEmotionType);
      oldHotStockList.forEach(hotStock -> hotStock.updateStock(newHotStockList));
    }
  }
}

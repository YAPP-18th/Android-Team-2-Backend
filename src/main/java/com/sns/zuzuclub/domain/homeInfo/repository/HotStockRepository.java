package com.sns.zuzuclub.domain.homeInfo.repository;

import com.sns.zuzuclub.constant.PostEmotionType;
import com.sns.zuzuclub.domain.homeInfo.model.HotStock;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotStockRepository extends JpaRepository<HotStock, Long> {

  List<HotStock> findAllByPostEmotionType(PostEmotionType postEmotionType);

}

package com.sns.zuzuclub.domain.homeInfo.repository;

import com.sns.zuzuclub.domain.homeInfo.model.Weather;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<Weather, Long> {
  Optional<Weather> findTopByOrderByIdDesc();
}

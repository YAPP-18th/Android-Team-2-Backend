package com.sns.zuzuclub.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeConvertor {

  public static String convertToString(LocalDateTime from) {

    LocalDateTime now = LocalDateTime.now();
    Duration duration = Duration.between(from, now);

    long days = duration.toDays();
    if (days >= 7) {
      return from.format(DateTimeFormatter.ofPattern("yyyy년-MM월-dd일 HH:mm:ss"));
    }
    if (days >= 1) {
      return days + "일 전";
    }

    long hours = duration.toHours();
    if (hours >= 1) {
      return hours + "시간 전";
    }

    long minutes = duration.toMinutes();
    if (minutes >= 1) {
      return minutes + "분 전";
    }

    return "방금 전";
  }
}

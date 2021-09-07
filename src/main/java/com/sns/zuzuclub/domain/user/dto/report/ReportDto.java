package com.sns.zuzuclub.domain.user.dto.report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class ReportDto {

  private String nickname;
  private int reportCount;
  private String suspendDays;
  private String startDate;
  private String endDate;

  public ReportDto(String nickname, int reportCount, LocalDateTime startDate) {
    this.nickname = nickname;
    this.reportCount = reportCount;
    this.suspendDays = calculateSuspendDays(reportCount);
    this.startDate = convertToString(startDate);
    this.endDate = calculateEndDate(reportCount, startDate);
  }

  private String calculateSuspendDays(int reportCount) {
    if (reportCount < 10){
      return "14";
    }
    return "30";
  }

  private String convertToString(LocalDateTime localDateTime) {
    return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  private String calculateEndDate(int reportCount, LocalDateTime startDate) {
    LocalDateTime endDate;
    if (reportCount < 10){
      endDate = startDate.plusDays(14);
    } else {
      endDate = startDate.plusDays(30);
    }
    return convertToString(endDate);
  }
}

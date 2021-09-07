package com.sns.zuzuclub.global.exception;

import com.sns.zuzuclub.domain.user.dto.report.ReportDto;
import com.sns.zuzuclub.global.exception.errorCodeType.UserErrorCodeType;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SuspendException extends RuntimeException {

  private final int errorCode;
  private final ReportDto reportDto;

  public SuspendException(UserErrorCodeType userErrorCodeType, String nickname, int reportCount, LocalDateTime startDate){
    super(userErrorCodeType.getMessage());
    this.errorCode = userErrorCodeType.getErrorCode();
    this.reportDto = new ReportDto(nickname, reportCount, startDate);
  }
}

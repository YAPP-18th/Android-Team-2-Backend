package com.sns.zuzuclub.global.exception;

import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {

  @ExceptionHandler(CustomException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public CommonResult CustomException(CustomException e) {
    log.error("{} : {}", e.getErrorCode(), e.getMessage());
    return ResponseForm.getFailResult(e.getErrorCode(), e.getMessage());
  }

}

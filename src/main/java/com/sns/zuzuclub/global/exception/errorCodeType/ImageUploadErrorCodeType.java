package com.sns.zuzuclub.global.exception.errorCodeType;

import lombok.Getter;

@Getter
public enum ImageUploadErrorCodeType {

  FILE_IO_ERROR(461, "파일 입출력 오류"),
  ALREADY_EXIST_FILE(462, "이미 있는 파일")
  ;

  private final int errorCode;
  private final String message;

  ImageUploadErrorCodeType(final int errorCode, final String message) {
    this.message = message;
    this.errorCode = errorCode;
  }
}

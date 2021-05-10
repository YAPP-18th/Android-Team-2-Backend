package com.sns.zuzuclub.global.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResult {

  @ApiModelProperty(value = "성공 : 200 / 실패 : ERROR_CODE")
  private int code;

  @ApiModelProperty(value = "응답 메시지")
  private String msg;
}

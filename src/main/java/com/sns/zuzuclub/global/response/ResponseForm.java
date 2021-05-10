package com.sns.zuzuclub.global.response;

import java.util.List;

public class ResponseForm {

  public static <T> SingleResult<T> getSingleResult(T data, String msg){
    SingleResult<T> result = new SingleResult<>();
    result.setData(data);
    result.setCode(200);
    result.setMsg(msg);
    return result;
  }

  public static <T> MultipleResult<T> getMultipleResult(List<T> list, String msg){
    MultipleResult<T> result = new MultipleResult<>();
    result.setList(list);
    result.setCode(200);
    result.setMsg(msg);
    return result;
  }

  public static CommonResult getSuccessResult(String msg){
    CommonResult result = new CommonResult();
    result.setCode(200);
    result.setMsg(msg);
    return result;
  }

  public static CommonResult getFailResult(int errorCode, String msg){
    CommonResult result = new CommonResult();
    result.setCode(errorCode);
    result.setMsg(msg);
    return result;
  }
}

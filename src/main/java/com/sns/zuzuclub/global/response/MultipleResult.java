package com.sns.zuzuclub.global.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultipleResult<T> extends CommonResult {
  private List<T> list;
}

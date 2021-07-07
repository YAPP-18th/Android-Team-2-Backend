package com.sns.zuzuclub.domain.user.dto.signup;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class SignupRequestDto {

  @ApiModelProperty(value = "닉네임", example = "잼민이")
  private String nickname;

  @ApiModelProperty(value = "소개", example = "와타시잼민이")
  private String introduction;

  @ApiModelProperty(value = "관심 종목 id 리스트")
  private List<Long> scrapStockIdList;

}

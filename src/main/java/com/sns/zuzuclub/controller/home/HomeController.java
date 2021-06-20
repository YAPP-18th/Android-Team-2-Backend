package com.sns.zuzuclub.controller.home;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.controller.home.dto.HomeResponseDto;
import com.sns.zuzuclub.controller.home.dto.UserScrapDto;
import com.sns.zuzuclub.controller.home.dto.UserStockScrapDto;
import com.sns.zuzuclub.domain.home.application.HomeInfoService;
import com.sns.zuzuclub.global.response.MultipleResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HomeController {

  private final JwtTokenProvider jwtTokenProvider;
  private final HomeInfoService homeInfoService;

  @ApiOperation(
      value = "홈 화면 정보",
      notes = "<h3>\n"
          + "- 전체 게시글에 언급된 감정들 중, 가장 많이 언급된 감정으로 날씨 값을 출력 \n"
          + "- 감정별 , 언급이 가장 많은 종목\n"
          + "- 프로필화면의 관심 종목 리스트 조회하기와 동일하게 동작하지만, 최근 추가순으로 6개만 출력\n"
          + "</h3>"
  )
  @GetMapping("/home")
  public SingleResult<HomeResponseDto> getHomeInfo(@RequestHeader(value = "Authorization") String jwtToken){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    HomeResponseDto homeResponseDto = homeInfoService.getHomeInfo(userId);
    log.info(homeResponseDto.toString());
    return ResponseForm.getSingleResult(homeResponseDto, "홈 화면 정보");
  }

  @ApiOperation(
      value = "홈 화면 - 나의 관심 종목 더보기",
      notes = "<h3>\n"
          + "- 나의 관심 종목 더보기 \n"
          + "</h3>"
  )
  @GetMapping("/home/scrap")
  public SingleResult<UserScrapDto> getUserStockScrap(@RequestHeader(value = "Authorization") String jwtToken){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    UserScrapDto userScrapDto = homeInfoService.getUserStockScrap(userId);
    log.info(userScrapDto.toString());
    return ResponseForm.getSingleResult(userScrapDto, "나의 관심 종목 더보기");
  }

}

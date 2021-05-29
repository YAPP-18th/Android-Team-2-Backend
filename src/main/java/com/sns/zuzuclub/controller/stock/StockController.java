package com.sns.zuzuclub.controller.stock;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.controller.stock.dto.StockResponseDto;
import com.sns.zuzuclub.domain.stock.application.StockService;
import com.sns.zuzuclub.global.response.CommonResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class StockController {

  private final StockService stockService;
  private final JwtTokenProvider jwtTokenProvider;

  @ApiOperation(
      value = "주식 종목 - 첫화면",
      notes = "<h3>\n"
          + "- 주식 종목의 첫화면을 불러옵니다. \n"
          + "</h3>"
  )
  @GetMapping("/stocks/{stockId}")
  public SingleResult<StockResponseDto> getStock(@RequestHeader(value = "Authorization") String jwtToken,
                                                 @ApiParam(value = "주식종목 Id", type = "Long", required = true) @PathVariable Long stockId) {

    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    StockResponseDto stockResponseDto = stockService.getStock(userId, stockId);
    log.info(stockResponseDto.toString());
    return ResponseForm.getSingleResult(stockResponseDto, "주식 종목 - 첫화면");
  }

  @ApiOperation(
      value = "주식 종목 - 관심 종목 추가",
      notes = "<h3>\n"
          + "- 로그인 유저의 관심 종목에 stockId에 해당하는 주식을 추가합니다.\n"
          + "</h3>"
  )
  @PostMapping("/users/stocks/{stockId}")
  public CommonResult createUserStockScrap(@RequestHeader(value = "Authorization") String jwtToken,
                                       @ApiParam(value = "주식종목 Id", type = "Long", required = true) @PathVariable Long stockId) {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    stockService.createUserStockScrap(userId, stockId);
    log.info("주식 종목 - 관심 종목 추가");
    return ResponseForm.getSuccessResult("주식 종목 - 관심 종목 추가");
  }

  @ApiOperation(
      value = "주식 종목 - 관심 종목 취소",
      notes = "<h3>\n"
          + "- 로그인 유저의 관심 종목에 stockId에 해당하는 주식을 취소합니다.\n"
          + "</h3>"
  )
  @DeleteMapping("/users/stocks/{stockId}")
  public CommonResult deleteUserStockScrap(@RequestHeader(value = "Authorization") String jwtToken,
                                         @ApiParam(value = "주식종목 Id", type = "Long", required = true) @PathVariable Long stockId) {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    stockService.deleteUserStockScrap(userId, stockId);
    log.info("주식 종목 - 관심 종목 취소");
    return ResponseForm.getSuccessResult("주식 종목 - 관심 종목 취소");
  }
}

package com.sns.zuzuclub.controller.search;


import com.sns.zuzuclub.controller.search.dto.SearchUserResponseDto;
import com.sns.zuzuclub.controller.signup.dto.StockResponseDto;
import com.sns.zuzuclub.domain.stock.application.SearchStockService;
import com.sns.zuzuclub.domain.user.application.SearchUserService;
import com.sns.zuzuclub.global.response.MultipleResult;
import com.sns.zuzuclub.global.response.ResponseForm;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class searchController {

  private final SearchUserService searchUserService;
  private final SearchStockService searchStockService;

  @ApiOperation(
      value = "닉네임 검색",
      notes = "<h3>\n"
          + " 검색어% 의 조건으로 찾아옵니다. \n"
          + "</h3>"
  )
//  // 각 메서드에 responseMessage를
//  @ApiResponses({
//      @ApiResponse(code = 200, message = "회원 목록 OK!!!"),
//      @ApiResponse(code = 404, message = "서버 문제 발생!!!"),
//      @ApiResponse(code = 500, message = "페이지를 찾을 수 없어!!!")
//  })
  @GetMapping("/users")
  public MultipleResult<SearchUserResponseDto> searchUser(@RequestHeader(value = "Authorization") String jwtToken, @RequestParam String nickname){
    List<SearchUserResponseDto> searchUserResponseDtoList = searchUserService.searchByNickname(nickname);
    return ResponseForm.getMultipleResult(searchUserResponseDtoList, "닉네임 검색");
  }

  @ApiOperation(
      value = "종목명 검색",
      notes = "<h3>\n"
          + " 검색어% 의 조건으로 찾아옵니다. \n"
          + "</h3>"
  )
//  // 각 메서드에 responseMessage를
//  @ApiResponses({
//      @ApiResponse(code = 200, message = "회원 목록 OK!!!"),
//      @ApiResponse(code = 404, message = "서버 문제 발생!!!"),
//      @ApiResponse(code = 500, message = "페이지를 찾을 수 없어!!!")
//  })
  @GetMapping("/stocks")
  public MultipleResult<StockResponseDto> searchStock(@RequestHeader(value = "Authorization") String jwtToken, @RequestParam String stockName){
    List<StockResponseDto> stockResponseDtoList =  searchStockService.searchStockByStockName(stockName);
    return ResponseForm.getMultipleResult(stockResponseDtoList, "종목명 검색");
  }

}

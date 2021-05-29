package com.sns.zuzuclub.controller.signup;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.controller.signup.dto.SignupRequestDto;
import com.sns.zuzuclub.controller.signup.dto.StockResponseDto;
import com.sns.zuzuclub.domain.user.application.SignupService;
import com.sns.zuzuclub.global.response.MultipleResult;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/signup")
@RequiredArgsConstructor
@RestController
public class SignupController {

  private final SignupService signupService;
  private final JwtTokenProvider jwtTokenProvider;

  @ApiOperation(
      value = "회원가입 - 닉네임 중복검사",
      notes = "<h3>\n"
          + "- DB에 중복되는 닉네임이 등록되어있는지 체크합니다.\n"
          + "- False 반환 시, 중복되는 닉네임이 없습니다."
          + "</h3>"
  )
  @PostMapping("/nickname")
  public SingleResult<Boolean> hasDuplicateNickname(@RequestHeader(value = "Authorization") String jwtToken, @ApiParam(value = "닉네임", type = "String", required = true) @RequestBody String nickname) {
    log.info(nickname);
    Boolean hasDuplicateNickname = signupService.hasDuplicateNickname(nickname);
    log.info("hasDuplicateNickname : " + hasDuplicateNickname.toString());
    return ResponseForm.getSingleResult(hasDuplicateNickname, "닉네임 중복 검사");
  }

  @ApiOperation(
      value = "관심 종목 목록을 불러옵니다.",
      notes = "<h3>\n"
          + "- 미리 정해둔 관심 종목 리스트 20개를 불러옵니다.\n"
          + "</h3>"
  )
  @GetMapping("/stocks")
  public MultipleResult<StockResponseDto> getStockList(@RequestHeader(value = "Authorization") String jwtToken) {
    List<StockResponseDto> stockListResponseDtoList = signupService.getStockList();
    log.info(stockListResponseDtoList.toString());
    return ResponseForm.getMultipleResult(stockListResponseDtoList, "관심 종목 목록 불러오기");
  }

  @ApiOperation(
      value = "회원정보 등록하기",
      notes = "<h3>\n"
          + "- 회원정보를 등록하고, 회원가입을 마무리합니다.\n"
          + "- 회원정보 등록할 때, 닉네임 중복검사를 한 번 더 시행??? 은 고민을 좀 해봐야할듯\n"
          + "- 도장쾅 화면을 위해서, 닉네임을 리턴합니다.\n"
          + "</h3>"
  )
  @PostMapping("/user")
  public SingleResult<String> registerUser(@RequestHeader(value = "Authorization") String jwtToken, @RequestBody SignupRequestDto signupRequestDto) {
    log.info(signupRequestDto.toString());
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    String nickname = signupService.registerUser(userId, signupRequestDto);
    log.info(nickname);
    return ResponseForm.getSingleResult(nickname, "회원정보 등록");
  }

}

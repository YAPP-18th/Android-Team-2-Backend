package com.sns.zuzuclub.controller.profile;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.controller.profile.dto.ProfileResponseDto;
import com.sns.zuzuclub.domain.user.application.ProfileService;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController {

  private final JwtTokenProvider jwtTokenProvider;
  private final ProfileService profileService;

  @ApiOperation(
      value = "나의 프로필",
      notes = "<h3>\n"
          + "하단 메뉴바에서 프로필 선택해서 나의 프로필로 들어갈 때 사용합니다."
          + "</h3>"
  )
//  // 각 메서드에 responseMessage를
//  @ApiResponses({
//      @ApiResponse(code = 200, message = "회원 목록 OK!!!"),
//      @ApiResponse(code = 404, message = "서버 문제 발생!!!"),
//      @ApiResponse(code = 500, message = "페이지를 찾을 수 없어!!!")
//  })
  @GetMapping("/profile")
  public SingleResult<ProfileResponseDto> getUserProfile(@RequestHeader(value = "Authorization") String jwtToken){
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    ProfileResponseDto profileResponseDto = profileService.getUserProfile(userId);
    return ResponseForm.getSingleResult(profileResponseDto, "프로필 페이지");
  }

}

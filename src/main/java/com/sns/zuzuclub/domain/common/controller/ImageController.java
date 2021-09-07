package com.sns.zuzuclub.domain.common.controller;

import com.sns.zuzuclub.config.security.JwtTokenProvider;
import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import com.sns.zuzuclub.infra.s3.S3Uploader;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImageController {

  private final S3Uploader s3Uploader;
  private final JwtTokenProvider jwtTokenProvider;

  @ApiOperation(
      value = "이미지 업로드",
      notes = "<h3>\n"
          + "multipart/form-data 형식으로 파일을 받아서 저장된 url 을 리턴 받습니다.\n"
          + "</h3>"
  )
  @PostMapping("/images")
  public SingleResult<String> uploadImage(@RequestHeader(value = "Authorization") String jwtToken,
                                          @RequestPart MultipartFile multipartFile) {
    Long userId = Long.valueOf(jwtTokenProvider.resolveUserPk(jwtToken));
    String imageUrl = s3Uploader.upload(userId, multipartFile);
    log.info(imageUrl);
    return ResponseForm.getSingleResult(imageUrl, "이미지 업로드");
  }
}

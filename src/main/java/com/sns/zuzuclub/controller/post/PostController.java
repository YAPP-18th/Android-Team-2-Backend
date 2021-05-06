package com.sns.zuzuclub.controller.post;


import com.sns.zuzuclub.global.response.ResponseForm;
import com.sns.zuzuclub.global.response.SingleResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  @GetMapping("/post")
  public String getPost() {
    return "post";
  }

  @GetMapping("/post/var/{some}")
  public SingleResult<String> getVar(@RequestHeader(value = "Authorization") String jwtToken, @PathVariable String some, @RequestParam String param) {
    return ResponseForm.getSingleResult(some + param, "성공띠");
  }
}

package com.sns.zuzuclub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticController {

  @GetMapping("/intro")
  public String getIntroductionPage(){
    return "/intro.html";
  }

  @GetMapping("/intro/privacy")
  public String getPrivacy(){
    return "/privacy.html";
  }

  @GetMapping("/intro/rules")
  public String getRules(){
    return "/rules.html";
  }
}


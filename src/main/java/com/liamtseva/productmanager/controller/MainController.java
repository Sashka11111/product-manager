package com.liamtseva.productmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
  public String showHomePage() {
    // Повертає HTML-сторінку
    return "index";  // сторінка буде шукатись у ресурсах під /templates/index.html
  }
}
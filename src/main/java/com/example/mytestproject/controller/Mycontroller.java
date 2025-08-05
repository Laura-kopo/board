package com.example.mytestproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //컨트롤러 선언
public class Mycontroller {
    //메서드 선언
    @GetMapping("/hello")
    public String hellMethod(Model model) {
        model.addAttribute("username", "홍길동");//키(string)와 값(object)으로 이루어짐
        return "greeting"; //view에게 전달
    }

    //메서드 선언
    @GetMapping("/bye")
    public String byeMethod(Model model) {
        model.addAttribute("username", "성춘향");
        return "bye"; //view에게 전달
    }
}

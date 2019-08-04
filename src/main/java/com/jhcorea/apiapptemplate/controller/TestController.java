package com.jhcorea.apiapptemplate.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//test용 인터셉터로 IP인증이나 기타 붙여놓는다.
//lazy에 대한 예시

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${phase.message}")
    String message;

    @GetMapping
    @RequestMapping("/phase")
    public String getPhase() {
        return message;
    }

    @GetMapping
    @RequestMapping("/case1")
    public String test1() {
        return "OK";
    }
}

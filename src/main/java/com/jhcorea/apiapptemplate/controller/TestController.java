package com.jhcorea.apiapptemplate.controller;

import com.jhcorea.apiapptemplate.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//test용 인터셉터로 IP인증이나 기타 붙여놓는다.
//lazy에 대한 예시

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    ApplicationContext context;

    @Value("${phase.common.message}")
    String message;

    @Value("${simple.message}")
    String simple;

    @Autowired
    TestService service;

    @GetMapping
    @RequestMapping("/phase")
    public String getPhase() {
        return message;
    }

    @GetMapping
    @RequestMapping("/case1")
    public String case1() {
        return "OK";
    }

    @GetMapping
    @RequestMapping("/log")
    public String logTest() {
        service.printLog();
        return "OK!";
    }
}

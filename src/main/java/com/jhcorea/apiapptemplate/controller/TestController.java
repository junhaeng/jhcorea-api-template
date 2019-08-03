package com.jhcorea.apiapptemplate.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/case1")
    public String test1() {
        return "OK";
    }
}

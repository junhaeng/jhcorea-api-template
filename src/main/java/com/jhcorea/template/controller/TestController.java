package com.jhcorea.template.controller;

import com.jhcorea.template.repository.entity.TemplateEntity;
import com.jhcorea.template.repository.readonly.ReadOnlyTemplateRepository;
import com.jhcorea.template.repository.writable.TemplateRepository;
import com.jhcorea.template.service.TestService;
import org.hibernate.sql.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//test용 인터셉터로 IP인증이나 기타 붙여놓는다.
//lazy에 대한 예시

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    ApplicationContext context;

    @Value("${simple.message}")
    String simple;

    @Autowired
    TestService service;

    @GetMapping
    @RequestMapping("/case1")
    public String case1() {
        return simple;
    }

    @GetMapping
    @RequestMapping("/log")
    public String logTest() {
        service.printLog();
        return "OK!";
    }

    @Autowired
    TemplateRepository templateRepository;

    @Autowired
    ReadOnlyTemplateRepository readOnlyTemplateRepository;

    @PostMapping("/add/readonly")
    public TemplateEntity addRandom() {
        TemplateEntity entity = readOnlyTemplateRepository.save(new TemplateEntity("test" + System.currentTimeMillis()));
        return entity;
    }

    @PostMapping("/add/writable")
    public TemplateEntity addRandom2() {
        TemplateEntity entity = templateRepository.save(new TemplateEntity("test" + System.currentTimeMillis()));
        return entity;
    }

}

package com.jhcorea.apiapptemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

//TODO StringUtils 들에 대한 고찰....CollectionUtils 또한..

@SpringBootApplication
public class ApiAppTemplateWarApplication {

	public static void main(String[] args) {
		String profile = System.getProperty("spring.profiles.active");
		if(StringUtils.isEmpty(profile)) {
			System.setProperty("spring.profiles.active", "local");
		}
		//TODO logback으로
		System.out.println("시스템 구동 단계 : " + System.getProperty("spring.profiles.active"));
		SpringApplication.run(ApiAppTemplateWarApplication.class, args);
	}

}

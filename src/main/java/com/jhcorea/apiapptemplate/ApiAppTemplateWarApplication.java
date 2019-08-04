package com.jhcorea.apiapptemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

//TODO StringUtils 들에 대한 고찰....CollectionUtils 또한..

@SpringBootApplication
public class ApiAppTemplateWarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiAppTemplateWarApplication.class, args);
	}

}

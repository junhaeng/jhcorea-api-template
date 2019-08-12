package com.jhcorea.template;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

//TODO StringUtils 들에 대한 고찰....CollectionUtils 또한..

@SpringBootApplication
public class ApiAppTemplateWarApplication {

	public static void main(String[] args) {
		ApplicationContext context =  SpringApplication.run(ApiAppTemplateWarApplication.class, args);
		for (String s : context.getBeanDefinitionNames()) {
			System.out.println("bean : " + s);
		}
	}

}

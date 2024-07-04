package com.springbootstudy.app.configurations;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/noticeWriteForm").setViewName("views/noticeWriteForm");
		registry.addViewController("/noticeWriteBoard").setViewName("views/noticeWriteForm");		
	}
}

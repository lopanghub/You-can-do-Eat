package com.springbootstudy.app.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override 
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/noticeWriteForm").setViewName("views/noticeWriteForm");
		registry.addViewController("/noticeWriteBoard").setViewName("views/noticeWriteForm");
		registry.addViewController("/shopMain").setViewName("views/shop/shopMain"); 

	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/profile/**")
				.addResourceLocations("file:./src/main/resources/static/profile/")
				.setCachePeriod(1);
	}
}

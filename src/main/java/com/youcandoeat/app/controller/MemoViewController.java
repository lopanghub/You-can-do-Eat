package com.youcandoeat.app.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.youcandoeat.app.service.MemoEntityService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/* Spring MVC Controller 클래스 임을 정의
* @Controller 애노테이션이 적용된 클래스의 메서드는 기본적으로 뷰의 이름을 반환한다.
**/
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemoViewController {
	// 클래스에 롬복의 @RequiredArgsConstructor가 적용되어 생성자를 통해 주입된다.
	private final MemoEntityService entityService;
	
	// @Controller 애노테이션이 적용된 클래스의 메서드는 뷰를 반환한다.
	@GetMapping("/thymeleaf01")
	public String thDefault01(Model model) {
		
		model.addAttribute("text", "<h1>hello Thymeleaf</h1>");
		model.addAttribute("memo", entityService.getMemo(1));
		model.addAttribute("today", LocalDate.now());
		
		// application.properties 파일에서 Thymeleaf 설정을 다음과 같이 설정했기 때문에
		// spring.thymeleaf.prefix=classpath:/templates/
		// spring.thymeleaf.view-names=th/*
		// th/* 반환되는 뷰는 Thymeleaf가 적용되어 templates/th 폴더에서 뷰를 찾는다.
		return "th/default1";
	}
	
	@GetMapping("/thymeleaf02/{no}")
	public String thDefault02(Model model, @PathVariable(name="no") int no) {
		log.info("thDefault02 -no : " + no);
		
		model.addAttribute("title", "<h1>Thymeleaf 사용하기2</h1>");
		model.addAttribute("mList", entityService.memoList());
		return "th/default2";
		
	}
	
	@GetMapping("/thymeleaf03")
	public String memoList(Model model, @RequestParam(value="no") int no) {
		log.info("thDefault3 - no : "+no);
		
		model.addAttribute("title", "<h1>Thymeleaf 사용하기3</h1>");
		model.addAttribute("memo", entityService.getMemo(no));
		return "th/default3";
	}
}

package com.springbootstudy.app.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.springbootstudy.app.domain.FAQ;

@Controller
public class FAQController {
	
	  @GetMapping("/faq")
	    public String faqPage(Model model) {
	        List<FAQ> faqs = Arrays.asList(
	            new FAQ("서비스 이용 시간은 어떻게 되나요?", "저희 서비스는 24시간 365일 이용 가능합니다."),
	            new FAQ("환불 정책은 어떻게 되나요?", "구매 후 7일 이내에는 전액 환불이 가능합니다. 7일 이후에는 개별 상황에 따라 처리됩니다."),
	            new FAQ("회원 가입은 어떻게 하나요?", "홈페이지 우측 상단의 '회원가입' 버튼을 클릭하신 후, 안내에 따라 정보를 입력하시면 됩니다.")
	        );
	        model.addAttribute("faqs", faqs);
	        return "views/faq";
	    }
	  
}

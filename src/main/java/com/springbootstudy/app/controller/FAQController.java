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
	        	new FAQ("레시피를 등록하는 방법은 어떻게 되나요?", "레시피 등록은 간단합니다. 먼저 로그인 후, 상단 메뉴에서 '레시피 등록' 버튼을 클릭하세요. 레시피 제목, 재료, 조리 과정, 완성된 요리 사진을 차례로 입력하시면 됩니다. 각 단계별로 상세한 설명과 팁을 추가하시면 다른 사용자들에게 더 유용한 정보를 제공할 수 있습니다."),
	            new FAQ("레시피 평가는 어떻게 하나요?", "각 레시피 하단에 있는 별점 시스템을 통해 평가하실 수 있습니다. 1점부터 5점까지 선택 가능하며, 평가와 함께 코멘트를 남기실 수 있습니다. 여러분의 솔직한 평가와 피드백은 레시피 작성자에게 큰 도움이 됩니다."),
	            new FAQ("비밀번호를 잊어버렸어요. 어떻게 해야 하나요?", "관리자에게 문의해주시면, 가입 시 등록한 이메일 주소로 알려드리겠습니다."),
	            new FAQ("모바일 앱은 언제 출시되나요?", "현재 모바일 앱을 개발 중에 있으며, 올해 말 출시를 목표로 하고 있습니다. 정확한 출시일은 추후 공지사항을 통해 안내될 예정입니다."),
	            new FAQ("회원 가입은 어떻게 하나요?", "홈페이지 우측 상단의 '회원가입' 버튼을 클릭하신 후, 안내에 따라 정보를 입력하시면 됩니다.")
	        );
	        model.addAttribute("faqs", faqs);
	        return "views/faq";
	    }
	  
}

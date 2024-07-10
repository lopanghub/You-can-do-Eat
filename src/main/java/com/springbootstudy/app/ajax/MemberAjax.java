package com.springbootstudy.app.ajax;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.service.MemberService;

@RestController
public class MemberAjax {
	
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/isIdCheck")
	public ResponseEntity<Map<String, Boolean>> idCheck(@RequestParam("id") String id){
		// true, false - id 비교하는메서드
		boolean idcheck = memberService.isIdcheck(id);
		Map<String, Boolean> result = new HashMap<>();
		result.put("result", idcheck);
		
		return ResponseEntity.ok().body(result);		
	}
	
	@GetMapping("/isPassCheck")
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> passCheck(@RequestParam("id") String id, @RequestParam("pass") String pass){
		// true, false - id 비교하는메서드
		Map<String, Boolean> result = new HashMap<>();
		boolean passcheck = memberService.isPasscheck(id, pass);
		result.put("result", passcheck);
		
		return ResponseEntity.ok().body(result);		
	}
	

}


package com.youcandoeat.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.youcandoeat.app.domain.Memo;
import com.youcandoeat.app.service.MemoService;

import lombok.RequiredArgsConstructor;

/* RestController 클래스 임을 정의
* @RestController 애노테이션은 @Controller에 @ResponseBody가
* 추가된 것과 동일하다. RestController의 주용도는 JSON으로 응답하는 것이다.
**/

@RestController
@RequiredArgsConstructor
public class MemoController {
	
	// 클래스에 롬복의 @RequiredArgsConstructor가 적용되어 생성자를 통해 주입된다.
	private final MemoService memoService;
	
	/* @RestController 애노테이션이 클래스에 적용되었기 때문에
	* 이 메서드에서 반환되는 값은 JSON으로 직렬화되어 응답 본문에 포함된다.
	**/
	@GetMapping("/memos")
	public List<Memo> memoList(){
		return memoService.memoList();
	}
	
	@GetMapping("/memos/{no}")
	public Memo getMemo(@PathVariable("no") int no) {
		return memoService.getMemo(no);
	}
	
	//메모 추가 요청 처리 메서드
	@PostMapping("/memos")
	public Map<String, Object> addMemo(Memo memo){
		memoService.addMemo(memo);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", true);
		resultMap.put("memo", memo);
		return resultMap;
	}
	
	//메모 수정 요청 처리 메서드
	@PutMapping("/memos")
	public Map<String, Object> updateMemo(Memo memo){
		memoService.updateMemo(memo);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", true);
		return resultMap;
	}
	
	//메모 삭제 요청 처리 메서드
	@DeleteMapping("/memos")
	public Map<String, Object> deleteMemo(@RequestParam("no") int no){
		memoService.deleteMemo(no);
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("result", true);
		return resultMap;
	}
}

package com.springbootstudy.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
=======
import org.springframework.boot.devtools.restart.server.HttpRestartServer;
>>>>>>> 45f71bb (new files)
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

<<<<<<< HEAD
import com.springbootstudy.app.service.RecipeService;

=======
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.service.RecipeService;

import jakarta.servlet.http.HttpSession;
>>>>>>> 45f71bb (new files)
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
	
<<<<<<< HEAD
=======
	// 레시피 리스트 출력(boardList)
>>>>>>> 45f71bb (new files)
	@GetMapping({"/","/recipeList"})
	public String recipeList(Model model) {
		model.addAttribute("rList",recipeService.RecipeBoardList());
		return "views/recipeList";
	}
	
<<<<<<< HEAD
	@GetMapping("/recipeDetail")
	public String getRecipe(Model model, @RequestParam("boardNo")int boardNo) {
		model.addAttribute("rList",recipeService.getRecipe(boardNo));
		model.addAttribute("bookCount",recipeService.cookCount(boardNo));
		model.addAttribute("cookingId",recipeService.cookIdCheck(boardNo));
		model.addAttribute("FristId",recipeService.cookIdCheck(boardNo).get(0));
=======
	// No의 상세보기 출력
	@GetMapping("/recipeDetail")
	public String getRecipe(Model model, @RequestParam("boardNo")int boardNo, HttpSession session) {
	    
		//id 세션저장 나중에 로그인 기능 나오면 삭제
	   String id = recipeService.getRecipe(boardNo).getMemberId();
	   session.setAttribute("id", id);
	    model.addAttribute("id",id);
	    
	    //  상세보기
		model.addAttribute("rList",recipeService.getRecipe(boardNo));
		// 책리스트 카운트
		model.addAttribute("bookCount",recipeService.cookCount(boardNo));
		// 조리과정 no의 초기값
		model.addAttribute("cookingId",recipeService.cookIdCheck(boardNo).get(0));
		// 재료리스트
>>>>>>> 45f71bb (new files)
		model.addAttribute("mList",recipeService.addMaterialList(boardNo));
		return "views/recipeDetail";
	}
}

package com.springbootstudy.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.service.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
	
	@GetMapping({"/","/recipeList"})
	public String recipeList(Model model) {
		model.addAttribute("rList",recipeService.RecipeBoardList());
		return "views/recipeList";
	}
	
	@GetMapping("/recipeDetail")
	public String getRecipe(Model model, @RequestParam("boardNo")int boardNo) {
		model.addAttribute("rList",recipeService.getRecipe(boardNo));
		model.addAttribute("bookCount",recipeService.cookCount(boardNo));
		model.addAttribute("cookingId",recipeService.cookIdCheck(boardNo).get(0));
		model.addAttribute("mList",recipeService.addMaterialList(boardNo));
		return "views/recipeDetail";
	}
}

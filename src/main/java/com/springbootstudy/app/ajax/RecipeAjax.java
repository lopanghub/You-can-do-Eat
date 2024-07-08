package com.springbootstudy.app.ajax;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.CookMaterial;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.RecipeBoard;
import com.springbootstudy.app.service.RecipeService;



@RestController

public class RecipeAjax {
	@Autowired
	private RecipeService recipeService; 
	
	
	//매
	
	
	
	// 조리과정리스트 - 조리리스트 버튼
	   @GetMapping("/ajax/recipeList")
	   
	    public List<Cooking> recipeList(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "cookingId") int cookingId) {
	    	 List<Cooking> recipe = recipeService.getCookList(boardNo);
	         return recipe;
	    } 
	   
	   //cookingId 의 재료리스트 -조리리스트 책 버튼
		@GetMapping("/ajax/recipeMList")

	    public List<CookMaterial> recipeMList(@RequestParam(name = "cookingId") int cookingId,@RequestParam(name = "boardNo") int boardNo) {
	    	
	        return recipeService.cookMaterList(cookingId,boardNo);
	    }
		
	
	
		// 조리과정리스트 -조리리스트 책 버튼
    @GetMapping("/ajax/cookList")

    public Cooking cookList(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "currentPage") int currentPage,@RequestParam(name = "cookingId") int cookingId) {
    	
        Cooking getCook = recipeService.getCookList(boardNo).get(currentPage);
         return getCook;
    }
    
    //cookingId 의 재료리스트 -조리리스트 책 버튼
	@GetMapping("/ajax/cookMList")
    public List<CookMaterial> cookMList(@RequestParam(name = "cookingId") int cookingId,@RequestParam(name = "boardNo") int boardNo) {
        return recipeService.cookMaterList(cookingId,boardNo);
    }
	
	// 책의 첫페이지  -조리리스트 책 버튼
	@GetMapping("/ajax/bookDetail")
	public RecipeBoard getBook(@RequestParam(name = "boardNo") int boardNo) {
		 RecipeBoard recipe = recipeService.getRecipe(boardNo);
		    recipe.setMaterials(recipeService.getMaterialList(boardNo));
		    return recipe;
	}
    
}

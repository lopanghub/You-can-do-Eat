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
	

    @GetMapping("/ajax/cookList")
    @ResponseBody
    public Cooking cookList(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "currentPage") int currentPage) {
        Cooking getCook = recipeService.addCookList(boardNo).get(currentPage);
         return getCook;
    }
	
	@GetMapping("/ajax/cookMList")
    @ResponseBody
    public List<CookMaterial> cookMList(@RequestParam(name = "cookingId") int cookingId,@RequestParam(name = "boardNo") int boardNo) {
        return recipeService.cookMaterList(cookingId,boardNo);
    }
	
	@GetMapping("/ajax/bookDetail")
	@ResponseBody
	public RecipeBoard getBook(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "currentPage") int currentPage) {
		 RecipeBoard recipe = recipeService.getRecipe(boardNo);
		    
		    recipe.setMaterialList(recipeService.addMaterialList(boardNo));
		    return recipe;
	}
    
}

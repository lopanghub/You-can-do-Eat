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
	
<<<<<<< HEAD
	@GetMapping("/ajax/cookList")
    @ResponseBody
    public List<Cooking> cookList(@RequestParam(name = "boardNo") int boardNo) {
        return recipeService.addCookList(boardNo);
    }
	
	@GetMapping("/ajax/cookMList")
    @ResponseBody
    public List<CookMaterial> cookList(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "cookingId") int cookingId) {
        return recipeService.cookMaterList(boardNo,cookingId);
    }
	
=======
		// 조리과정리스트
    @GetMapping("/ajax/cookList")
    @ResponseBody
    public Cooking cookList(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "currentPage") int currentPage,@RequestParam(name = "cookingId") int cookingId) {
    	
        Cooking getCook = recipeService.addCookList(boardNo).get(currentPage);
         return getCook;
    }
	
    //cookingId 의 재료리스트
	@GetMapping("/ajax/cookMList")
    @ResponseBody
    public List<CookMaterial> cookMList(@RequestParam(name = "cookingId") int cookingId,@RequestParam(name = "boardNo") int boardNo) {
    	System.out.println("cookMList의 cookingId :" +cookingId);
        return recipeService.cookMaterList(cookingId,boardNo);
    }
	
	// 책의 첫페이지 
>>>>>>> 45f71bb (new files)
	@GetMapping("/ajax/bookDetail")
	@ResponseBody
	public RecipeBoard getBook(@RequestParam(name = "boardNo") int boardNo) {
		 RecipeBoard recipe = recipeService.getRecipe(boardNo);
<<<<<<< HEAD
		    
		    recipe.setMaterialList(recipeService.addMaterialList(boardNo));
		    System.out.println("ajax 요청이 들어오긴함"+ recipe.getBoardView()+recipe.getMaterialList().get(0));
=======
		    recipe.setMaterialList(recipeService.addMaterialList(boardNo));
>>>>>>> 45f71bb (new files)
		    return recipe;
	}
    
}

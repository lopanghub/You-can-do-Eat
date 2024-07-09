package com.springbootstudy.app.ajax;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.springbootstudy.app.domain.CookMaterial;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.Material;
import com.springbootstudy.app.domain.RecipeBoard;
import com.springbootstudy.app.service.RecipeService;



@RestController
public class RecipeAjax {
	@Autowired
	private RecipeService recipeService; 
	
	// 조리과정리스트 - 조리리스트 버튼
	   @GetMapping("/ajax/recipeList")
	    @ResponseBody
	    public List<Cooking> recipeList(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "cookingId") int cookingId) {
	    	 List<Cooking> recipe = recipeService.getCookList(boardNo);
	         return recipe;
	    } 
	   
	   //cookingId 의 재료리스트 -조리리스트 책 버튼
		@GetMapping("/ajax/recipeMList")
	    @ResponseBody
	    public List<CookMaterial> recipeMList(@RequestParam(name = "cookingId") int cookingId,@RequestParam(name = "boardNo") int boardNo) {
	    	
	        return recipeService.cookMaterList(cookingId,boardNo);
	    }
		
	
	
		// 조리과정리스트 -조리리스트 책 버튼
    @GetMapping("/ajax/cookList")
    @ResponseBody
    public Cooking cookList(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "currentPage") int currentPage,@RequestParam(name = "cookingId") int cookingId) {
    	
        Cooking getCook = recipeService.getCookList(boardNo).get(currentPage);
         return getCook;
    }
    
    //cookingId 의 재료리스트 -조리리스트 책 버튼
	@GetMapping("/ajax/cookMList")
    @ResponseBody
    public List<CookMaterial> cookMList(@RequestParam(name = "cookingId") int cookingId,@RequestParam(name = "boardNo") int boardNo) {
        return recipeService.cookMaterList(cookingId,boardNo);
    }
	
	// 책의 첫페이지  -조리리스트 책 버튼
	@GetMapping("/ajax/bookDetail")
	@ResponseBody
	public RecipeBoard getBook(@RequestParam(name = "boardNo") int boardNo) {
		 RecipeBoard recipe = recipeService.getRecipe(boardNo);
		    recipe.setMaterials(recipeService.getMaterialList(boardNo));
		    return recipe;
	}
	@PostMapping("/ajax/cookUpdate")
	@ResponseBody
	public List<Cooking> updateCookAjax(@RequestParam(name="boardNo")int boardNo) {
		List<Cooking> cList = recipeService.getCookList(boardNo);
		return cList;
	}
	@PostMapping("/ajax/materialUpdate")
	@ResponseBody
	public List<Material> updateMaterialAjax(@RequestParam(name="boardNo")int boardNo) {
		List<Material> mList = recipeService.getMaterialList(boardNo);
		// 재료리스트
		return mList;
		
	}
    
}

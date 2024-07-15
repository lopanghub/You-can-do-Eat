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

import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.domain.CookMaterial;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.Material;
import com.springbootstudy.app.domain.RecipeBoard;
import com.springbootstudy.app.dto.commentDTO;
import com.springbootstudy.app.service.CommentService;
import com.springbootstudy.app.service.RecipeService;



@RestController
public class RecipeAjax {
	@Autowired
	private RecipeService recipeService; 
	@Autowired
	private CommentService commentService;
	
	// 조리과정리스트 - 조리리스트 버튼
	   @GetMapping("/ajax/recipeList")
	   
	    public List<Cooking> recipeList(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "cookingId") int cookingId) {
	    	 List<Cooking> recipe = recipeService.getCookList(boardNo);
	         return recipe;
	    } 
	   
	
	
		// 조리과정리스트 -조리리스트 책 버튼
    @GetMapping("/ajax/cookList")

    public Cooking cookList(@RequestParam(name = "boardNo") int boardNo,@RequestParam(name = "currentPage") int currentPage,@RequestParam(name = "cookingId") int cookingId) {
        Cooking getCook = recipeService.getCookList(boardNo).get(currentPage);
         return getCook;
    }
    
	
	// 책의 첫페이지  -조리리스트 책 버튼
	@GetMapping("/ajax/bookDetail")
	public RecipeBoard getBook(Model model,@RequestParam(name = "boardNo") int boardNo) {
		 RecipeBoard recipe = recipeService.getRecipe(boardNo,false);
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

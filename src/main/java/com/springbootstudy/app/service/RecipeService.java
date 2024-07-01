package com.springbootstudy.app.service;

<<<<<<< HEAD
import java.util.List;
=======
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> 45f71bb (new files)
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.CookMaterial;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.Material;
import com.springbootstudy.app.domain.RecipeBoard;
import com.springbootstudy.app.mapper.RecipeMapper;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Return;

@Service
@Slf4j
public class RecipeService {
	@Autowired
	public RecipeMapper recipeMapper;
	
	public List<RecipeBoard> RecipeBoardList(){
		return recipeMapper.RecipeBoardList();
	}
	public RecipeBoard getRecipe(int BoardNo) {
		return recipeMapper.getBoard(BoardNo);
	}
	
<<<<<<< HEAD
	 public List<Cooking> addCookList(int boardNo) {
	        // 추가할 요리 목록을 조회
	        return recipeMapper.addCookList(boardNo);

	    }
=======
	public List<Cooking> addCookList(int boardNo) {
        return recipeMapper.addCookList(boardNo);
    }
>>>>>>> 45f71bb (new files)
	 public int cookCount(int boardNo) {
		 return recipeMapper.cookCount(boardNo);
	 }

	    public List<Material> addMaterialList(int boardNo) {
	        // 추가할 재료 목록을 조회
	    	return recipeMapper.addMaterialList(boardNo);

	    }
	    public List<CookMaterial> cookMaterList(int cookingId , int boardNo){
<<<<<<< HEAD
	    	return recipeMapper.CookMaterList(cookingId, boardNo);
=======
	    	System.out.println("RecipeService-cookingId: "+cookingId);
	    	System.out.println("RecipeService-boardNo: "+boardNo);
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	params.put("cookingId", cookingId);
	    	params.put("boardNo", boardNo);
	    	
	    	return recipeMapper.CookMaterList(params);
>>>>>>> 45f71bb (new files)
	    }
	    
	    public List<Cooking> cookIdCheck(int boardNo) {
	    	return recipeMapper.CookidCheck(boardNo);
	    }
    }


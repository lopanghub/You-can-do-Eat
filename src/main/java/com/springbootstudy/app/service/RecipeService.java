package com.springbootstudy.app.service;

import java.util.List;
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
	
	 public List<Cooking> addCookList(int boardNo) {
	        // 추가할 요리 목록을 조회
	        return recipeMapper.addCookList(boardNo);

	    }
	 public int cookCount(int boardNo) {
		 return recipeMapper.cookCount(boardNo);
	 }

	    public List<Material> addMaterialList(int boardNo) {
	        // 추가할 재료 목록을 조회
	    	return recipeMapper.addMaterialList(boardNo);

	    }
	    public List<CookMaterial> cookMaterList(int cookingId , int boardNo){
	    	return recipeMapper.CookMaterList(cookingId, boardNo);
	    }
	    
	    public List<Cooking> cookIdCheck(int boardNo) {
	    	return recipeMapper.CookidCheck(boardNo);
	    }
    }


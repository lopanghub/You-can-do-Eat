package com.springbootstudy.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	
	//레시피 리스트
	public List<RecipeBoard> RecipeBoardList(){
		return recipeMapper.recipeBoardList();
	}
	
	//boardno 레시피
	public RecipeBoard getRecipe(int BoardNo) {
		return recipeMapper.getBoard(BoardNo);
	}
	
	//boardNo의 cooking 조리방법리스트
	public List<Cooking> getCookList(int boardNo) {
        return recipeMapper.getCookList(boardNo);
    }
	
	//boardno의 cooking 카운트 (책페이지수)
	 public int cookCount(int boardNo) {
		 return recipeMapper.cookCount(boardNo);
	 }
	 	//boardNo 의 재료
	    public List<Material> getMaterialList(int boardNo) {
	    	return recipeMapper.getMaterialList(boardNo);
	    }
	    //boardNo 의 cooking 각 요리과정 재료 리스트
	    public List<CookMaterial> cookMaterList(int cookingId , int boardNo){
	    	
	    	Map<String, Object> params = new HashMap<>();
	    	params.put("cookingId", cookingId);
	    	params.put("boardNo", boardNo);
	    	
	    	return recipeMapper.cookMaterList(params);
	    }
	    
	    // cookingId 의시작 을 알기위한 매서드
	    public List<Integer> cookIdCheck(int boardNo) {
	    	return recipeMapper.cookidCheck(boardNo);
	    }
	    
	    
	    //추가하는 메서드
	    public void addRecipe(String thumbnail, String foodName, String boardTitle,  String boardContent,
              String foodGenre,  int numberEaters,  int foodTime, List<Material> materials,
               List<Cooking> cookings, List<CookMaterial> CookMaterials) {
	    	RecipeBoard recipeBoard = new RecipeBoard();
	    	recipeBoard.setBoardContent(boardContent);
	    	recipeBoard.setBoardTitle(boardTitle);
	    	recipeBoard.setFoodName(foodName);
	    	recipeBoard.setFoodGenre(foodGenre);
	    	recipeBoard.setNumberEaters(numberEaters);
	    	recipeBoard.setFoodTime(foodTime);
	    	
	    	recipeBoard.setCookingList(cookings);
	    	recipeBoard.setMaterialList(materials);
	    	
	    	recipeMapper.insertRecipe(recipeBoard);
	    }
    }


package com.springbootstudy.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springbootstudy.app.domain.CookMaterial;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.Material;
import com.springbootstudy.app.domain.RecipeBoard;
import com.springbootstudy.app.mapper.RecipeMapper;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Return;

@Service
@Slf4j
public class RecipeService {
	@Autowired
	public RecipeMapper recipeMapper;
	
	private static final int PAGE_SIZE = 10;

	private static final int PAGE_GROUP=10;

	// 레시피 리스트
	public Map<String, Object>  RecipeBoardList(int pageNum, String type, String keyword) {
		int currentPage = pageNum;
		System.out.println("currentPage 의 값 : " + currentPage);
		int offset = (currentPage -1)* PAGE_SIZE;
		Map<String,Object> param1 = new HashMap();
		param1.put("type", type);
		param1.put("keyword", keyword);
		int listCount = recipeMapper.getRecipeCount(param1);
		Map<String,Object> param = new HashMap<>();
		
		param.put("type", type);
		param.put("keyword", keyword);
		param.put("offset", offset);
		param.put("limit", PAGE_SIZE);	
		List<RecipeBoard> recipeList = recipeMapper.recipeBoardList(param);
		
		int pageCount= listCount /PAGE_SIZE + (listCount % PAGE_SIZE == 0 ? 0 : 1);
		
		int startPage = (currentPage / PAGE_GROUP) * PAGE_GROUP + 1
				- (currentPage % PAGE_GROUP == 0 ? PAGE_GROUP : 0);
				// 현재 페이지 그룹의 마지막 페이지 : 10, 20, 30...
		int endPage = startPage + PAGE_GROUP - 1;
		
		if(endPage > pageCount) {
			endPage = pageCount;
			}
		boolean searchOption = (type.equals("null")
				|| keyword.equals("null")) ? false : true;
		
		
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("recipeList",recipeList );
			modelMap.put("pageCount", pageCount);
			modelMap.put("startPage", startPage);
			modelMap.put("endPage", endPage);
			modelMap.put("currentPage", currentPage);
			modelMap.put("listCount", listCount);
			modelMap.put("pageGroup", PAGE_GROUP);
			modelMap.put("searchOption", searchOption);
			if(searchOption) {
			modelMap.put("type", type);
			modelMap.put("keyword", keyword);
			}
			return modelMap;
	}

	
	 @Transactional public void updateApoint(int boardNo ,double Apoint) {
		  Map<String, Object> param = new HashMap(); 
		  System.out.println("averagePoint 총점수 : "+ Apoint);
		  param.put("Apoint", Apoint);
		  param.put("boardNo", boardNo);
		  recipeMapper.updateApoint(param); 
	  }
	 
	// boardno 레시피
	public RecipeBoard getRecipe(int BoardNo, boolean isCount) {
		if(isCount) {
			recipeMapper.readCount(BoardNo);
		}
		return recipeMapper.getBoard(BoardNo);
	}

	// boardNo의 cooking 조리방법리스트
	public List<Cooking> getCookList(int boardNo) {
		return recipeMapper.getCookList(boardNo);
	}

	// boardno의 cooking 카운트 (책페이지수)
	public int cookCount(int boardNo) {
		return recipeMapper.cookCount(boardNo);
	}

	// boardNo 의 재료
	public List<Material> getMaterialList(int boardNo) {
		return recipeMapper.getMaterialList(boardNo);
	}

	

	// cookingId 의시작 을 알기위한 매서드
	public List<Integer> cookIdCheck(int boardNo) {
		return recipeMapper.cookidCheck(boardNo);
	}

	// 레시피 추가
	@Transactional
	public void addRecipe(RecipeBoard recipeBoard) throws Exception {
		recipeMapper.insertRecipe(recipeBoard);
	}

	// 재료 추가하는 메서드
	@Transactional
	public void addMaterial(int boardNo,Material material) throws Exception {
		material.setBoardNo(boardNo);
				recipeMapper.insertMaterial(material); // 각 재료를 데이터베이스에 저장

	}

	// 요리과정 추가하는 매서드
	@Transactional
	public void addCooking(int boardNo, Cooking cooking) throws Exception {
		cooking.setBoardNo(boardNo);
		    recipeMapper.insertCooking(cooking);
	}
	// 요리과정의 재료 추가하는 매서드

	
	// 레시피 업데이트
	@Transactional
	public void updateRecipe(String boardTitle,String boardContent,String foodGenre,int numberEaters,int foodTime,String filename, int boardNo) throws Exception {
		Map<String, Object> params = new HashMap<>();
	
		params.put("boardTitle", boardTitle);
		params.put("boardContent", boardContent);
		params.put("foodGenre", foodGenre);
		params.put("numberEaters", numberEaters);
		params.put("numberEaters", numberEaters);
		params.put("foodTime", foodTime);
		params.put("filename", filename);
		params.put("boardNo", boardNo);
		recipeMapper.updateRecipe(params);
	}
	
	// 재료 업데이트
	@Transactional
	public void updateMaterial(int boardNo,String materialName,String mensuration,String typeMaterial) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("boardNo", boardNo);
		params.put("materialName", materialName);
		params.put("mensuration", mensuration);
		params.put("typeMaterial", typeMaterial);
		
		recipeMapper.updateMaterial(params); // 각 재료를 데이터베이스에 저장
		
	}
	
	// 요리과정 업데이트
	@Transactional
	public void updateCooking(int boardNo,String cookTitle,String cookMethod,String recommended,String filename) throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("boardNo", boardNo);
		params.put("cookTitle",cookTitle);
		params.put("cookMethod",cookMethod);
		params.put("recommended",recommended);
		params.put("filename",filename);
		recipeMapper.updateCooking(params);
	}
	// 요리과정의 재료 업데이트
	
	public void deleteByNo(int boardNo) {
		recipeMapper.deleteMaterialByNo(boardNo);
		recipeMapper.deleteCookingByNo(boardNo);
	}
	
	
	// boardNo 삭제
	public void deleteRecipe(int boardNo) {
		
		recipeMapper.deleteCookingByNo(boardNo);
		recipeMapper.deleteMaterialByNo(boardNo);
		recipeMapper.deleteRecipe(boardNo);
	}

}

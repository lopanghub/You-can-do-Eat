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

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.Return;

@Service
@Slf4j
public class RecipeService {
	@Autowired
	public RecipeMapper recipeMapper;


	// 레시피 리스트
	public List<RecipeBoard> RecipeBoardList() {
		return recipeMapper.recipeBoardList();
	}

	// boardno 레시피
	public RecipeBoard getRecipe(int BoardNo) {
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

	// boardNo 의 cooking 각 요리과정 재료 리스트
	public List<CookMaterial> cookMaterList(int cookingId, int boardNo) {

		Map<String, Object> params = new HashMap<>();
		params.put("cookingId", cookingId);
		params.put("boardNo", boardNo);
		return recipeMapper.cookMaterList(params);
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
	public void addMaterial(Material material) throws Exception {
				recipeMapper.insertMaterial(material); // 각 재료를 데이터베이스에 저장

	}

	// 요리과정 추가하는 매서드
	@Transactional
	public void addCooking(int boardNo, Cooking cooking) throws Exception {
		cooking.setBoardNo(boardNo);
		    recipeMapper.insertCooking(cooking);
	}
	// 요리과정의 재료 추가하는 매서드

	@Transactional
	public void addCookMaterial(int cookingId, int boardNo, List<CookMaterial> cookMaterials) throws Exception {
		if (cookMaterials != null) {
			for (CookMaterial CookMaterial : cookMaterials) {
				CookMaterial.setCookingId(cookingId);
				CookMaterial.setBoardNo(boardNo);
				recipeMapper.insertCookMaterial(CookMaterial);

			}
		}
	}
	// 레시피 업데이트
	@Transactional
	public void updateRecipe(RecipeBoard recipeBoard) throws Exception {
		recipeMapper.updateRecipe(recipeBoard);
	}
	
	// 재료 업데이트
	@Transactional
	public void updateMaterial(Material material) throws Exception {
		recipeMapper.updateMatrial(material); // 각 재료를 데이터베이스에 저장
		
	}
	
	// 요리과정 업데이트
	@Transactional
	public void updateCooking(int boardNo, Cooking cooking) throws Exception {
		cooking.setBoardNo(boardNo);
		System.out.println("시스탬 쿠키파일 :"+cooking.getCookFile());
		recipeMapper.updateCooking(cooking);
	}
	// 요리과정의 재료 업데이트
	
	@Transactional
	public void updateCookMaterial(int cookingId, int boardNo, List<CookMaterial> cookMaterials) throws Exception {
		if (cookMaterials != null) {
			for (CookMaterial CookMaterial : cookMaterials) {
				CookMaterial.setCookingId(cookingId);
				CookMaterial.setBoardNo(boardNo);
				System.out.println("서비스에 cookingId : " + cookingId);
				System.out.println("서비스에 boardNo : " + boardNo);
				recipeMapper.updateCookMaterial(CookMaterial);
				
			}
		}
	}
	
	@Transactional
	public List<CookMaterial> cookMaterListByNo(int boardNo){
		return recipeMapper.cookMaterListByNo(boardNo);
	}
	
	
	// boardNo 삭제
	public void deleteRecipe(int boardNo) {
		recipeMapper.deleteCookMatrailByNo(boardNo);
		recipeMapper.deleteCookingByNo(boardNo);
		recipeMapper.deleteMaterialByNo(boardNo);
		recipeMapper.deleteRecipe(boardNo);
	}

}

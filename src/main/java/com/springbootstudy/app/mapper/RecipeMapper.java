package com.springbootstudy.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springbootstudy.app.domain.CookMaterial;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.Material;
import com.springbootstudy.app.domain.RecipeBoard;

@Mapper
public interface RecipeMapper {
	// boardNo의 레시피 요리 순서
	  List<Cooking> getCookList(@Param("boardNo") int boardNo);
		
		// boardNo의 레시피 요리페이지 카운트
		int cookCount(int boardNo);

		// boardNo의 재료들
		List<Material> getMaterialList(int boardNo);
		
		// BoardList
		List<RecipeBoard> recipeBoardList();
		// boardNo의 상세보기
		RecipeBoard getBoard(int boardNo);
		
		// boardNo의 조리과정의 재료들
		List<CookMaterial> cookMaterList(Map<String, Object> params);
		
		// boardNo의 cookingId 출력
		List<Integer> cookidCheck(int boardNo);
		
		//boardNo의 cookMaterial 출력
		List<CookMaterial> cookMaterListByNo(int boardNo);
		
		//레시피 추가하기
		void insertRecipe(RecipeBoard recipeBoard);
		//레시피에 요리재료 추가하기
		void insertMaterial(Material materials);
		//레시피 요리 리스트 출력하기
		void insertCooking(Cooking cooking);
		//레시피에 요리과정재료 추가하기
		void insertCookMaterial(CookMaterial cookMaterial);
		
		//레시피삭제
		void deleteRecipe(int boardNo);
		//재료삭제
		void deleteMaterialByNo(int board);
		//요리과정 삭제
		void deleteCookingByNo(int board);
		//요리과정 재료 삭제
		void deleteCookMatrailByNo(int board);
		
		//레시피 업데이트
		void updateRecipe(RecipeBoard recipeBoard);
		//레시피에 요리재료 업데이트
		void updateMatrial(Material material);
		
		//레시피 요리 리스트 업데이트
		void updateCooking(Cooking cooking);
		
		//레시피에 요리과정재료 업데이트
		void updateCookMaterial(CookMaterial cookMaterial);
		
}

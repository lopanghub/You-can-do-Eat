package com.springbootstudy.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
		// 조회수 증가 
	  int readCount(int boardNo);
	  
	  
		// boardNo의 레시피 요리페이지 카운트
		int cookCount(int boardNo);
		// boardNo의 Apoint(평가점수)업데이트
		int updateApoint(Map<String,Object> param);
		// boardNo의 재료들
		List<Material> getMaterialList(int boardNo);
		
		// BoardList
		List<RecipeBoard> recipeBoardList(Map<String,Object> params);
		// BoardCount
		int getRecipeCount();
		
		
		// boardNo의 상세보기
		RecipeBoard getBoard(int boardNo);
		
		
		// boardNo의 cookingId 출력
		List<Integer> cookidCheck(int boardNo);
		
		
		//레시피 추가하기
		void insertRecipe(RecipeBoard recipeBoard);
		//레시피에 요리재료 추가하기
		void insertMaterial(Material material);
		//레시피 요리 리스트 출력하기
		void insertCooking(Cooking cooking);
		
		//레시피삭제
		void deleteRecipe(int boardNo);
		//재료삭제
		void deleteMaterialByNo(int board);
		//요리과정 삭제
		void deleteCookingByNo(int board);
		
		//레시피 업데이트
		void updateRecipe(Map<String, Object>  params);
		//레시피에 요리재료 업데이트
		void updateMaterial(Map<String, Object>  params);
		
		//레시피 요리 리스트 업데이트
		void updateCooking(Map<String, Object>  params);
		
	
		List<Material> getMaterialListByNoId(Map<String, Object> params);
		
		
		//담당자 -이현학 가지고 온 데이터 삽입 매퍼
	    void insertRecipeBoard(RecipeBoard recipeBoard);
	    //담당자 - 이현학 데이터가 있을시 데이터 삽입 금지
	    @Select("SELECT COUNT(*) > 0 FROM recipe_board WHERE board_title = #{boardTitle}")
	    boolean existsByTitle(String boardTitle);

}

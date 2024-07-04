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
		
		//레시피에 요리재료 
		
}

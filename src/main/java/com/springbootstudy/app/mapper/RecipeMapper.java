package com.springbootstudy.app.mapper;

import java.util.List;
<<<<<<< HEAD
=======
import java.util.Map;
>>>>>>> 45f71bb (new files)

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
<<<<<<< HEAD
		List<Cooking> addCookList(int boardNo);
=======
	  List<Cooking> addCookList(@Param("boardNo") int boardNo);
		
>>>>>>> 45f71bb (new files)
		// boardNo의 레시피 요리페이지 카운트
		int cookCount(int boardNo);

		// boardNo의 재료들
		List<Material> addMaterialList(int boardNo);
		
		// BoardList
		List<RecipeBoard> RecipeBoardList();
		// boardNo의 상세보기
		RecipeBoard getBoard(int boardNo);
		
		// boardNo의 
<<<<<<< HEAD
		List<CookMaterial> CookMaterList(int cookingId, int boardNo);
=======
		List<CookMaterial> CookMaterList(Map<String, Object> params);
>>>>>>> 45f71bb (new files)
		
		List<Cooking> CookidCheck(int boardNo);
}

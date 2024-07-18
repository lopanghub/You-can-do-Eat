package com.springbootstudy.app.dto;

import java.sql.Timestamp;
import java.util.List;

import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.Material;
import com.springbootstudy.app.domain.RecipeBoard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class recipeListDTO {
	 private List<Cooking> cookings;
	    private List<Material> materials;
	    private RecipeBoard recipe;
}

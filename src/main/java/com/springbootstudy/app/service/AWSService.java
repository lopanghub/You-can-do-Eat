package com.springbootstudy.app.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.springbootstudy.app.domain.RecipeBoard;
import com.springbootstudy.app.mapper.RecipeMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AWSService {

    private final S3Service s3Service;
    private final RecipeMapper recipeMapper;

    @PostConstruct
    public void init() {
        String key = "recipes_updated_with_details.json";
        String jsonContent = s3Service.downloadFile(key);

        if (jsonContent != null) {
            List<RecipeBoard> recipes = parseRecipesFromJson(jsonContent);
            for (RecipeBoard recipe : recipes) {
            	if (!recipeMapper.existsByTitle(recipe.getBoardTitle())) {
                    recipeMapper.insertRecipeBoard(recipe);
                }
            }
        }
    }

    private List<RecipeBoard> parseRecipesFromJson(String jsonContent) {
        List<RecipeBoard> recipes = new ArrayList<>();

        JsonArray jsonArray = JsonParser.parseString(jsonContent).getAsJsonArray();
        
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            RecipeBoard recipeBoard = RecipeBoard.builder()
                    .boardTitle(jsonObject.get("title").getAsString())
                    .boardContent(jsonObject.has("recipeIntro") ? jsonObject.get("recipeIntro").getAsString() : null)
                    .foodGenre(jsonObject.get("category").getAsString())
                    .boardView(0) // 초기 조회수 0으로 설정
                    .regDate(new Timestamp(System.currentTimeMillis()))
                    .thumbnail(jsonObject.get("image_url").getAsString())
                    .foodTime(jsonObject.has("cookingTime") ? parseCookingTime(jsonObject.get("cookingTime").getAsString()) : 0)
                    .numberEaters(jsonObject.has("servingSize") ? parseServingSize(jsonObject.get("servingSize").getAsString()) : 0)
//                    .apoint(jsonObject.has("ratingCount") ? jsonObject.get("ratingCount").getAsDouble() : 0) // ratingCount 설정
                    .memberId(jsonObject.get("author_name").getAsString())
                    .build();
            recipes.add(recipeBoard);
        }

        return recipes;
    }

    private int parseCookingTime(String cookingTime) {
        if (cookingTime.contains("분")) {
            return Integer.parseInt(cookingTime.replaceAll("[^0-9]", ""));
        } else if (cookingTime.contains("시간")) {
            return Integer.parseInt(cookingTime.replaceAll("[^0-9]", "")) * 60;
        }
        return 0;
    }

    private int parseServingSize(String servingSize) {
        try {
            return Integer.parseInt(servingSize.replaceAll("[^0-9]", ""));
        } catch (NumberFormatException e) {
            return 0; // 기본값 설정
        }
    }
}

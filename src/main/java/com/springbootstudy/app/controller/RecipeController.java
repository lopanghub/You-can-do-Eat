package com.springbootstudy.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.restart.server.HttpRestartServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springbootstudy.app.domain.CookMaterial;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.Material;
import com.springbootstudy.app.service.CommentService;
import com.springbootstudy.app.service.RecipeService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController {
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private CommentService commentService;
	
	//레시피 리스트 쓰기
	@GetMapping("/recipeWrite")
	public String recipeWrite() {
		return "views/recipeWrite";
	}
	
	   private static String UPLOAD_DIR = "uploads/";

	   @PostMapping("/recipeWriteProcess")
	    public String writeRecipe(@RequestParam("thumbnail") MultipartFile thumbnail,
	                             @RequestParam("foodName") String foodName,
	                             @RequestParam("boardTitle") String boardTitle,
	                             @RequestParam("boardContent") String boardContent,
	                             @RequestParam("foodGenre") String foodGenre,
	                             @RequestParam("numberEaters") int numberEaters,
	                             @RequestParam("foodTime") int foodTime,
	                             @RequestParam("materials") List<String> materialNames,
	                             @RequestParam("mensurations") List<String> mensurations,
	                             @RequestParam("typeMaterials") List<String> typeMaterials,
	                             @RequestParam("cookTitles") List<String> cookTitles,
	                             @RequestParam("cookMethods") List<String> cookMethods,
	                             @RequestParam("recommendeds") List<String> recommendeds,
	                             @RequestParam("cookFiles") List<MultipartFile> cookFiles,
	                             Model model) {
	        if (thumbnail.isEmpty()) {
	            model.addAttribute("message", "Please select a file to upload");
	            return "redirect:/";
	        }

	        try {
	            // Save the thumbnail file
	            byte[] bytes = thumbnail.getBytes();
	            Path path = Paths.get(UPLOAD_DIR + thumbnail.getOriginalFilename());
	            Files.write(path, bytes);

	            // Process materials and cooking steps
	            for (int i = 0; i < materialNames.size(); i++) {
	                // Save each material
	                String materialName = materialNames.get(i);
	                String mensuration = mensurations.get(i);
	                String typeMaterial = typeMaterials.get(i);
	                // Save or process material data as needed
	            }

	            for (int i = 0; i < cookTitles.size(); i++) {
	                // Save each cooking step
	                String cookTitle = cookTitles.get(i);
	                String cookMethod = cookMethods.get(i);
	                String recommended = recommendeds.get(i);

	                if (!cookFiles.get(i).isEmpty()) {
	                    // Save each cook file
	                    byte[] cookFileBytes = cookFiles.get(i).getBytes();
	                    Path cookFilePath = Paths.get(UPLOAD_DIR + cookFiles.get(i).getOriginalFilename());
	                    Files.write(cookFilePath, cookFileBytes);
	                }

	                // Save or process cooking step data as needed
	            }

	            // Add attributes to the model
	            model.addAttribute("foodName", foodName);
	            model.addAttribute("boardTitle", boardTitle);
	            model.addAttribute("boardContent", boardContent);
	            model.addAttribute("foodGenre", foodGenre);
	            model.addAttribute("numberEaters", numberEaters);
	            model.addAttribute("foodTime", foodTime);
	            model.addAttribute("fileName", thumbnail.getOriginalFilename());

	            // Return success page
	            return "uploadSuccess";
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        return "redirect:/recipeList";
	    }
	
	
	// 레시피 리스트 출력(boardList)
	@GetMapping({"/","/recipeList"})
	public String recipeList(Model model) {
		model.addAttribute("rList",recipeService.RecipeBoardList());
		return "views/recipeList";
	}
	
	// No의 상세보기 출력
	@GetMapping("/recipeDetail")
	public String getRecipe(Model model,@RequestParam("boardNo")int boardNo, HttpSession session) {
	    
		//id 세션저장 나중에 로그인 기능 나오면 삭제
	   String id = recipeService.getRecipe(boardNo).getMemberId();
	   session.setAttribute("id", id);
	    model.addAttribute("id",id);
	    
	    
	  //평균 점수 계산
	    double averagePoint = commentService.calculateAveragePoint(boardNo);
	    //댓글리스트
	    model.addAttribute("commentList",  commentService.commentList(boardNo));
	    //댓글리스트 카운트
	    model.addAttribute("commentCount",  commentService.commentCount(boardNo));
	    
	    
	    // 평균점수
        model.addAttribute("averagePoint", averagePoint);
        
        
        // 평균점수 별점
        StringBuilder symbols = new StringBuilder();
        
        int i = 0;
        int Ipoint = (int) (averagePoint * 10) / 10;
        // 첫 번째 for 문
	        for (int j = 0; j < Ipoint; j++) {
		            symbols.append(" <i class=\"bi bi-star-fill\"></i>");
		            i++;
	        }
        // if 문
        if ((averagePoint * 10) % 10 > 1) {
        	if(i<5) {
            symbols.append(" <i class=\"bi bi-star-half\"></i>");
            i++;
        	}
        }
        // 두 번째 for 문
        for (int j = i; j < 5; j++) {
                symbols.append(" <i class=\"bi bi-star\"></i>");
                i++;
        }
        //평균점수의 별아이콘
        model.addAttribute("stars", symbols.toString());
	    
	    
	    
	    // 조리과정의 재료리스트
	    int cCount =recipeService.cookCount(boardNo);	    
	    for(int z=0; z<cCount;z++) {
		    int cookingId = recipeService.cookIdCheck(boardNo).get(z);
		    model.addAttribute("cMList"+z, recipeService.cookMaterList(cookingId,boardNo));
	    }
	    
	    //조리과정리스트
	    model.addAttribute("cList",  recipeService.getCookList(boardNo));
	    //  상세보기
		model.addAttribute("rList",recipeService.getRecipe(boardNo));
		// 책리스트 카운트
		model.addAttribute("bookCount",recipeService.cookCount(boardNo));
		// 조리과정 no의 초기값
		model.addAttribute("cookingId",recipeService.cookIdCheck(boardNo).get(0));
		// 재료리스트
		model.addAttribute("mList",recipeService.getMaterialList(boardNo));
		return "views/listRecipe-temp";
	}
	
}

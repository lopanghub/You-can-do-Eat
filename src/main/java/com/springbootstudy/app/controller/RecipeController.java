package com.springbootstudy.app.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;

import com.springbootstudy.app.domain.CookMaterial;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.Material;
import com.springbootstudy.app.domain.RecipeBoard;
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
	   private static String COOK_DIR = "cookloads/";

	   @PostMapping("/recipeWriteProcess")
	    public ModelAndView writeRecipe(@ModelAttribute RecipeBoard recipeBoard,@RequestParam("cookings") List<MultipartFile> cookingFiles, @RequestParam("thumbnailname") MultipartFile thumbnailname)throws Exception {
	        ModelAndView modelAndView = new ModelAndView();
	       
	            if (thumbnailname != null && !thumbnailname.isEmpty()) {
	            	  String uploadDir = "uploads/";
	                  String filename = System.currentTimeMillis() + "-" + thumbnailname.getOriginalFilename(); // 파일명 중복 방지
	                  Path path = Paths.get(uploadDir + filename);
	                  Files.createDirectories(path.getParent());
	                  Files.write(path, thumbnailname.getBytes());
	                  recipeBoard.setThumbnail(uploadDir + filename); // 파일 경로 저장
	            }
	            // 레시피 및 관련 데이터를 데이터베이스에 저장
	            recipeService.addRecipe(recipeBoard);
	            int boardNo =recipeBoard.getBoardNo();
	            if (recipeBoard.getMaterials() != null && !recipeBoard.getMaterials().isEmpty()) {
	            	System.out.println("여기는 컨트롤러구간" + recipeBoard.getMaterials().get(0).getMaterialName());
	                recipeService.addMaterial(boardNo, recipeBoard.getMaterials());
	                
	            }
	            if (recipeBoard.getCookings() != null && !recipeBoard.getCookings().isEmpty()) {
	                for (int i = 0; i < cookingFiles.size(); i++) {
	                    if (!cookingFiles.get(i).isEmpty()) {
	                        String uploadCookDir = "uploads/cooking/";
	                        String fileCookname = System.currentTimeMillis() + "-" + cookingFiles.get(i).getOriginalFilename();
	                        Path cookPath = Paths.get(uploadCookDir + fileCookname);
	                        Files.createDirectories(cookPath.getParent());
	                        Files.write(cookPath, cookingFiles.get(i).getBytes());
	                        // 조리 파일의 URL을 적절한 위치에 저장
	                        recipeBoard.getCookings().get(i).setCookFileUrl(uploadCookDir + fileCookname);
	                    }
	                }
	                recipeService.addCooking(boardNo, recipeBoard.getCookings());
	            }
	           
	            
	            for(int i=0;i< recipeBoard.getCookings().size();i++){
	            	int cookingId = recipeService.cookIdCheck(boardNo).get(i);
	            	System.out.println("컨트롤러 cookingId :" + cookingId);
	            	recipeService.addCookMaterial(cookingId,boardNo, recipeBoard.getCookings().get(i).getCookMaterials());	
	            	
	            }
	           
	            
	            modelAndView.setViewName("redirect:/recipeList");
	        return modelAndView;
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
		return "views/recipeDetail";
	}
	
	@GetMapping("recipeUpdateForm")
	public String recipeUpdateForm(@RequestParam("boardNo")int boardNo,Model model) {
		    
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
			// 재료리스트
			model.addAttribute("mList",recipeService.getMaterialList(boardNo));
		
		return "views/recipeUpdate";
	}
	
}

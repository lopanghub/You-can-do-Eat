package com.springbootstudy.app.controller;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.github.pagehelper.PageInfo;
import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.domain.CookMaterial;
import com.springbootstudy.app.domain.Cooking;
import com.springbootstudy.app.domain.Material;
import com.springbootstudy.app.domain.RecipeBoard;
import com.springbootstudy.app.dto.commentDTO;
import com.springbootstudy.app.service.CommentService;
import com.springbootstudy.app.service.RecipeService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController {
	@Autowired
	private RecipeService recipeService;

	@Autowired
	private CommentService commentService;

	// 레시피 리스트 쓰기
	@GetMapping("/recipeWrite")
	public String recipeWrite() {
		return "views/recipe/recipeWrite";
	}

	@PostMapping("/recipeWriteProcess")
	public String writeRecipe(
			@ModelAttribute RecipeBoard recipeBoard,
			
			@RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardContent") String boardContent,
			@RequestParam("foodGenre") String foodGenre, 
			@RequestParam("thumbnailname") MultipartFile thumbnailname,
			@RequestParam("numberEaters") int numberEaters, 
			@RequestParam("foodTime") int foodTime, 
			@RequestParam("cookTitles") List<String> cookTitles, 
			@RequestParam("cookMethods") List<String> cookMethods,
			@RequestParam("recommendeds") List<String> recommendeds,
			@RequestParam("cookFiles") List<MultipartFile> cookFiles,
			@RequestParam("materialNames") List<String> materialNames,
			@RequestParam("mensurations") List<String> mensurations,
			@RequestParam("typeMaterials") List<String> typeMaterials
			) throws Exception {
		System.out.println("recommendeds");
		RecipeBoard recipeBoard1 = new RecipeBoard();
		try {
		    if (thumbnailname != null && !thumbnailname.isEmpty()) {
		        String uploadDir = "bin/main/static/uploads/";
		        String filename = System.currentTimeMillis() + "-" + thumbnailname.getOriginalFilename(); // 파일명 중복 방지
		        Path path = Paths.get(uploadDir + filename);
		        Files.createDirectories(path.getParent());
		        Files.write(path, thumbnailname.getBytes());
		        recipeBoard1.setThumbnail(filename); // 파일 경로 저장
		    } else {
		        // 파일이 제대로 전송되지 않았을 때의 처리
		        System.out.println("파일이 전송되지 않았거나 비어 있습니다.");
		    }
		} catch (IOException e) {
		    // 파일 쓰기 중 예외 발생 시 처리
		    e.printStackTrace();
		}
		
		recipeBoard1.setBoardTitle(boardTitle);
		recipeBoard1.setBoardContent(boardContent);
		recipeBoard1.setFoodGenre(foodGenre);
		recipeBoard1.setNumberEaters(numberEaters);
		recipeBoard1.setFoodTime(foodTime);
		recipeService.addRecipe(recipeBoard1);
		int boardNo =recipeBoard1.getBoardNo();
		
		Cooking cooking = new Cooking();

		if (cookTitles.size() > 0) {
			
			for (int i = 0; i < cookTitles.size(); i++) {
				String cookTitle = cookTitles.get(i);
				cooking.setCookTitle(cookTitle);
				String cookMethod = cookMethods.get(i);
				cooking.setCookMethod(cookMethod);
				if (recommendeds != null && !recommendeds.isEmpty()) {
					String recommended=recommendeds.get(i);
					cooking.setRecommended(recommended);
				}else {
					cooking.setRecommended("");
				}
				
				MultipartFile cookMultiFile = cookFiles.get(i);
				
				if (cookMultiFile != null && !cookMultiFile.isEmpty()) {
					String uploadDir = "bin/main/static/uploads/cooking/";
					String filename = System.currentTimeMillis() + "-" + cookMultiFile.getOriginalFilename(); // 파일명 중복 방지
					Path path = Paths.get(uploadDir + filename);
					Files.createDirectories(path.getParent());
					Files.write(path, cookMultiFile.getBytes());
					cooking.setCookFile(filename); // 파일 경로 저장
				}else {
					cooking.setCookFile("");
				}
				recipeService.addCooking(boardNo,cooking);
			}
		}
		Material material = new Material();
		 if(materialNames.size()>0) {
			 for(int i=0; i<materialNames.size();i++) {
				 String materialName=materialNames.get(i);
				 material.setMaterialName(materialName);
				 String mensuration = mensurations.get(i);
				 material.setMensuration(mensuration);
				 String typeMaterial = typeMaterials.get(i);
				 material.setTypeMaterial(typeMaterial);
				 recipeService.addMaterial(boardNo,material);
			 }
		 }
		
		return "redirect:recipeList";
	}
	@PostMapping("/recipeUpdateProcess")
	public String updateRecipe(
			@RequestParam("boardNo")int boardNo,
			@RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardContent") String boardContent,
			@RequestParam("foodGenre") String foodGenre, 
			@RequestParam("thumbnailname") MultipartFile thumbnailname,
			@RequestParam("numberEaters") int numberEaters, 
			@RequestParam("foodTime") int foodTime, 
			@RequestParam("cookTitles") List<String> cookTitles, 
			@RequestParam("cookMethods") List<String> cookMethods,
			@RequestParam("recommendeds") List<String> recommendeds,
			@RequestParam("cookFiles") List<MultipartFile> cookFiles,
			@RequestParam("materialNames") List<String> materialNames,
			@RequestParam("mensurations") List<String> mensurations,
			@RequestParam("typeMaterials") List<String> typeMaterials) throws Exception {
		System.out.println("board No는"+ boardNo);
		String filename=null;
		try {
			if (thumbnailname != null && !thumbnailname.isEmpty()) {
				String uploadDir = "bin/main/static/uploads/";
				filename = System.currentTimeMillis() + "-" + thumbnailname.getOriginalFilename(); // 파일명 중복 방지
				Path path = Paths.get(uploadDir + filename);
				Files.createDirectories(path.getParent());
				Files.write(path, thumbnailname.getBytes());
			} else {
				// 파일이 제대로 전송되지 않았을 때의 처리
				System.out.println("파일이 전송되지 않았거나 비어 있습니다.");
			}
		} catch (IOException e) {
			// 파일 쓰기 중 예외 발생 시 처리
			e.printStackTrace();
		}
		recipeService.updateRecipe(boardTitle,boardContent,foodGenre,numberEaters,foodTime,filename, boardNo);
		
		recipeService.deleteByNo(boardNo);
		Cooking cooking = new Cooking();
		if (cookTitles.size() > 0) {
			for (int i = 0; i < cookTitles.size(); i++) {
				String cookTitle = cookTitles.get(i);
				cooking.setCookTitle(cookTitle);
				String cookMethod = cookMethods.get(i);
				cooking.setCookMethod(cookMethod);
				String recommended = recommendeds.get(i);
				cooking.setRecommended(recommended);
				MultipartFile cookMultiFile = cookFiles.get(i);
				
				if (cookMultiFile != null && !cookMultiFile.isEmpty()) {
					String uploadDir = "bin/main/static/uploads/cooking/";
					filename = System.currentTimeMillis() + "-" + cookMultiFile.getOriginalFilename(); // 파일명 중복 방지
					Path path = Paths.get(uploadDir + filename);
					Files.createDirectories(path.getParent());
					Files.write(path, cookMultiFile.getBytes());
					System.out.println(filename);
					cooking.setCookFile(filename); // 파일 경로 저장
				}else {
					cooking.setCookFile("");
				}
				
				recipeService.addCooking(boardNo,cooking);
			}
		}
		Material material = new Material();
		 if(materialNames.size()>0) {
			 for(int i=0; i<materialNames.size();i++) {
				 String materialName=materialNames.get(i);
				 material.setMaterialName(materialName);
				 String mensuration = mensurations.get(i);
				 material.setMensuration(mensuration);
				 String typeMaterial = typeMaterials.get(i);
				 material.setTypeMaterial(typeMaterial);
				 recipeService.addMaterial(boardNo,material);
			 }
		 }
		
		
		return "redirect:recipeList";
	}

	// 레시피 리스트 출력(boardList)
	@GetMapping({ "/", "/recipeList" })
	public String recipeList(Model model ,@RequestParam(value="pageNum", required=false,
			defaultValue="1") int pageNum) {
		// Service 클래스를 이용해 게시 글 리스트를 가져온다.
		Map<String, Object> modelMap = recipeService.RecipeBoardList(pageNum);
		model.addAllAttributes(modelMap);
		System.out.println(modelMap);
		return "views/recipe/recipeList";
	}
	
	

	// No의 상세보기 출력
	@GetMapping("/recipeDetail")
	public String getRecipe(Model model,@RequestParam("boardNo") int boardNo) throws UnsupportedEncodingException {

		 // 평균 점수 계산
	    double averagePoint = commentService.calculateAveragePoint(boardNo);
		System.out.println("averagePoint 총점수 : "+ averagePoint);
		 recipeService.updateApoint(boardNo,averagePoint);
		 	    
	    // 댓글 리스트와 댓글 수
	    List<Comment> commentList = commentService.commentList(boardNo);
	    int commentCount = commentService.commentCount(boardNo);
	    model.addAttribute("commentList",commentList);
	    model.addAttribute("commentCount",commentCount);
	    // 평균 점수 별점
	    StringBuilder symbols = new StringBuilder();
	    int i = 0;
	    int Ipoint = (int) (averagePoint * 10) / 10;
	    for (int j = 0; j < Ipoint; j++) {
	        symbols.append(" <i class=\"bi bi-star-fill\"></i>");
	        i++;
	    }
	    if ((averagePoint * 10) % 10 > 1) {
	        if (i < 5) {
	            symbols.append(" <i class=\"bi bi-star-half\"></i>");
	            i++;
	        }
	    }
	    for (int j = i; j < 5; j++) {
	        symbols.append(" <i class=\"bi bi-star\"></i>");
	        i++;
	    }
	    String stars = symbols.toString();
	    
	    model.addAttribute("stars",stars);
		
		
		// 조리과정의 재료리스트
	
		// 조리과정리스트
		RecipeBoard recipeBoard =  recipeService.getRecipe(boardNo,true);
		// 상세보기
		model.addAttribute("rList", recipeBoard);
		// 책리스트 카운트
		model.addAttribute("bookCount", recipeService.cookCount(boardNo));
		// 조리과정 no의 초기값
		model.addAttribute("cookingId", recipeService.cookIdCheck(boardNo).get(0));
		// 재료리스트
		model.addAttribute("mList", recipeService.getMaterialList(boardNo));
		model.addAttribute("cList", recipeService.getCookList(boardNo));
		return "views/recipe/recipeDetail";
	}

	@GetMapping("/updateRecipeForm")
	public String recipeUpdateForm(@RequestParam("boardNo") int boardNo, Model model) {
		// 조리과정의 재료리스트
		model.addAttribute("mList",recipeService.getMaterialList(boardNo));
		// 상세보기
		model.addAttribute("rList", recipeService.getRecipe(boardNo,false));
		// 요리리스트
		recipeService.getCookList(boardNo);
		model.addAttribute("cList", recipeService.getCookList(boardNo));
		
		return "views/recipe/recipeUpdate";
	}

	@PostMapping("/deleteRecipe")
	public String recipeDelete(@RequestParam("boardNo") int boardNo) throws IOException {
		commentService.deleteCommentByNo(boardNo);
		recipeService.deleteRecipe(boardNo);

		return "redirect:recipeList";
	}
}

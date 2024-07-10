package com.springbootstudy.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.http.HttpResponse;
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
		return "views/recipeWrite";
	}

	@PostMapping("/recipeWriteProcess")
	public String writeRecipe(@RequestParam("foodName") String foodName,
			@RequestParam("boardTitle") String boardTitle,
			@RequestParam("boardContent") String boardContent,
			@RequestParam("foodGenre") String foodGenre, 
			@RequestParam("thumbnailname") MultipartFile thumbnailname,
			@RequestParam("numberEaters") int numberEaters, 
			@RequestParam("materialNames") List<String> materialNames,
			@RequestParam("mensurations") List<String> mensurations,
			@RequestParam("typeMaterials") List<String> typeMaterials,
			@RequestParam("cookTitles") List<String> cookTitles, 
			@RequestParam("cookMethods") List<String> cookMethods,
			@RequestParam("recommendeds") List<String> recommendeds,
			@RequestParam("cookFiles") List<MultipartFile> cookFiles) throws Exception {
		RecipeBoard recipeBoard = new RecipeBoard();
		if (thumbnailname != null && !thumbnailname.isEmpty()) {
			String uploadDir = "/src/main/resources/static/uploads/";
			String filename = System.currentTimeMillis() + "-" + thumbnailname.getOriginalFilename(); // 파일명 중복 방지
			Path path = Paths.get(uploadDir + filename);
			Files.createDirectories(path.getParent());
			Files.write(path, thumbnailname.getBytes());
			recipeBoard.setThumbnail(uploadDir + filename); // 파일 경로 저장
		}
		recipeBoard.setFoodName(foodName);
		recipeBoard.setBoardTitle(boardTitle);
		recipeBoard.setBoardContent(boardContent);
		recipeBoard.setFoodGenre(foodGenre);
		recipeBoard.setNumberEaters(numberEaters);
		recipeService.addRecipe(recipeBoard);
		int boardNo =recipeBoard.getBoardNo();
		Material material = new Material();
		if (materialNames.size() > 0) {
			for (int i = 0; i < materialNames.size(); i++) {
				String materialName = materialNames.get(i);
				material.setMaterialName(materialName);
				String mensuration = mensurations.get(i);
				material.setMensuration(mensuration);
				String typeMaterial = typeMaterials.get(i);
				material.setTypeMaterial(typeMaterial);
				recipeService.addMaterial(boardNo, material);
			}
		}
		
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
					String uploadDir = "/src/main/resources/static/uploads/cookFile";
					String filename = System.currentTimeMillis() + "-" + cookMultiFile.getOriginalFilename(); // 파일명 중복 방지
					Path path = Paths.get(uploadDir + filename);
					Files.createDirectories(path.getParent());
					Files.write(path, cookMultiFile.getBytes());
					cooking.setCookFile(uploadDir + filename); // 파일 경로 저장
				}
				recipeService.addCooking(boardNo,cooking);
			}
		}
		
		return "redirect:recipeList";
	}

	// 레시피 리스트 출력(boardList)
	@GetMapping({ "/", "/recipeList" })
	public String recipeList(Model model) {
		model.addAttribute("rList", recipeService.RecipeBoardList());
		return "views/recipeList";
	}

	// No의 상세보기 출력
	@GetMapping("/recipeDetail")
	public String getRecipe(Model model, @RequestParam("boardNo") int boardNo, HttpSession session) {

		// id 세션저장 나중에 로그인 기능 나오면 삭제
		String id = recipeService.getRecipe(boardNo).getMemberId();
		session.setAttribute("id", id);
		model.addAttribute("id", id);

		// 평균 점수 계산
		double averagePoint = commentService.calculateAveragePoint(boardNo);
		// 댓글리스트
		model.addAttribute("commentList", commentService.commentList(boardNo));
		// 댓글리스트 카운트
		model.addAttribute("commentCount", commentService.commentCount(boardNo));

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
			if (i < 5) {
				symbols.append(" <i class=\"bi bi-star-half\"></i>");
				i++;
			}
		}
		// 두 번째 for 문
		for (int j = i; j < 5; j++) {
			symbols.append(" <i class=\"bi bi-star\"></i>");
			i++;
		}
		// 평균점수의 별아이콘
		model.addAttribute("stars", symbols.toString());

		// 조리과정의 재료리스트
		int cCount = recipeService.cookCount(boardNo);
		for (int z = 0; z < cCount; z++) {
			int cookingId = recipeService.cookIdCheck(boardNo).get(z);
			model.addAttribute("cMList" + z, recipeService.cookMaterList(cookingId, boardNo));

		}

		// 조리과정리스트
		model.addAttribute("cList", recipeService.getCookList(boardNo));
		// 상세보기
		model.addAttribute("rList", recipeService.getRecipe(boardNo));
		// 책리스트 카운트
		model.addAttribute("bookCount", recipeService.cookCount(boardNo));
		// 조리과정 no의 초기값
		model.addAttribute("cookingId", recipeService.cookIdCheck(boardNo).get(0));
		// 재료리스트
		model.addAttribute("mList", recipeService.getMaterialList(boardNo));
		return "views/recipeDetail";
	}

	@GetMapping("/updateRecipeForm")
	public String recipeUpdateForm(@RequestParam("boardNo") int boardNo, Model model) {
		System.out.println("update board no" + boardNo);
		// 조리과정의 재료리스트
		int cCount = recipeService.cookCount(boardNo);
		for (int z = 0; z < cCount; z++) {
			int cookingId = recipeService.cookIdCheck(boardNo).get(z);
			model.addAttribute("cMList" + z, recipeService.cookMaterList(cookingId, boardNo));
		}

		// 상세보기
		model.addAttribute("rList", recipeService.getRecipe(boardNo));
		// 재료리스트
		model.addAttribute("mList", recipeService.getMaterialList(boardNo));

		return "views/recipeUpdate";
	}

	@PostMapping("/deleteRecipe")
	public String recipeDelete(@RequestParam("boardNo") int boardNo, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();

		out.println("<script>");
		out.println("if(confirm('정말 삭제하시겠습니까?')) {");
		out.println("    window.location.href = 'deleteConfirmedRecipe?boardNo=" + boardNo + "';");
		out.println("} else {");
		out.println("    history.back();");
		out.println("}");
		out.println("</script>");

		commentService.deleteCommentByNo(boardNo);
		recipeService.deleteRecipe(boardNo);

		return "redirect:recipeList";
	}
}

package com.springbootstudy.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.boot.model.source.spi.CascadeStyleSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.resource.HttpResource;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;
import com.springbootstudy.app.domain.Community;
import com.springbootstudy.app.service.CommunityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommunityController {
	
	private final CommunityService communityService;
	
	private static final String DEFAULT_PATH  = "src/main/resources/static/files/community/";
	
	@GetMapping("/communityFileDownload")
	public void communityFileDownload(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		
		String fileName = req.getParameter("fileName");
		log.info("fileName : " + fileName);
		
		File communityFile = new File(DEFAULT_PATH);
		File file = new File(communityFile.getAbsolutePath(), fileName);
		log.info("file.getName() : " + file.getName());
		
		// 파일 타입 설정
		resp.setContentType("application/download; charset=UTF-8");
		resp.setContentLength((int) file.length());
		
		// 한글 인코딩
		fileName = URLEncoder.encode(file.getName(), "UTF-8");
		log.info("다운로드 : " + fileName);
		
		// 파일 이름 그대로 보내는
		resp.setHeader("Content-Disposition","attachMent; filename=\"" + fileName + "\";");
		
		resp.setHeader("Content-Transfer-Encoding", "binary");
		
		// 응답 스트림
		OutputStream out = resp.getOutputStream();
		FileInputStream fis =  null;
		
		fis = new FileInputStream(file);
		
		FileCopyUtils.copy(fis, out);
		
		if(fis != null) {
			fis.close();
		}
		
		out.flush();
	}
	
	
	@GetMapping("/communityList")
	public String communityBoardList(Model model) {
	        model.addAttribute("commuList", communityService.communityList());
	        return "views/community/communityList"; 
	    }
	
	@GetMapping("/communityDetail")
	public String communityBoardDetail(Model model, @RequestParam("no") int no) {
		communityService.incrementReadCount(no);
		model.addAttribute("community", communityService.getCommunityBoard(no));
		return "views/community/communityDetail";
	}
	
	@GetMapping("/addCommunityBoard")
	public String addCommunityBoard() {
		return "views/community/communityWriteForm";
	}
	
	@PostMapping("/addCommunityBoard")
	public String addCommunityBoard(Community community,
			HttpServletRequest req,
			@RequestParam(value="addFile", required=false) MultipartFile multipartFile) throws IOException{
		// 관리자 아이디만 등록 가능
		/* noticeBoard.setWriter("admin");
		 * noticeBoard.setPass("admin"); */
		System.out.println("originName : " + multipartFile.getOriginalFilename());
		System.out.println("name : " + multipartFile.getName());
		
		if(multipartFile != null && !multipartFile.isEmpty()) {
			File communityFile = new File(DEFAULT_PATH);
			log.info("communityFile 절대경로 : " + communityFile.getAbsolutePath());
			log.info("communityFile 경로 : " + communityFile.getPath());
			log.info("exist : " + communityFile.exists() + ", dir : " + communityFile.isDirectory());
		
		if (!communityFile.isDirectory() && !communityFile.exists()) {
			communityFile.mkdirs();
		}
		
		UUID uid = UUID.randomUUID();
		String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		String saveName = multipartFile.getOriginalFilename();
		
		File file = new File(communityFile.getAbsolutePath(), saveName);
		log.info("file 절대경로 : " + file.getAbsolutePath());
		log.info("file 경로 : " + file.getPath());
		
		multipartFile.transferTo(file);
		community.setFile1(saveName);
		
		} else {
			log.info("파일이 업로드 되지 않았습니다.");
		}
		
		communityService.addCommunityBoard(community);
		return "redirect:/communityList";
	}
	
	@PostMapping("/communityUpdateForm")
	public String updateCommunityBoard(Model model,
			@RequestParam("no") int no) {
		
		Community community = communityService.getCommunityBoard(no);
		
		
		model.addAttribute("community", community);
	    return "views/community/communityUpdateForm";
	}
	
	@PostMapping("/communityUpdate")
	public String updateCommunityBoard (Community community,
			HttpServletResponse resp, PrintWriter out) {
		
		Community communityBoard = communityService.getCommunityBoard(community.getNo());
		
		
		communityService.updateCommunityBoard(community);
		return "redirect:/communityList";
	}
	
	@PostMapping("/deleteCommunity")
	public String deleteCommunityBoard(
			HttpServletResponse resp, PrintWriter out, 
			@RequestParam("no") int no) {
		
		Community community = communityService.getCommunityBoard(no);
		
		
		communityService.deleteCommunityBoard(no);
		return "redirect:/communityList";
	}
	

}

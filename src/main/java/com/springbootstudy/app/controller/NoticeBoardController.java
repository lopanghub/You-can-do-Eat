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
import com.springbootstudy.app.domain.NoticeBoard;
import com.springbootstudy.app.service.NoticeBoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class NoticeBoardController {
	
	private final NoticeBoardService noticeBoardService;
	
	private static final String DEFAULT_PATH  = "src/main/resources/static/files/notice/";
	
	@GetMapping("/noticeFileDownload")
	public void noticeFileDownload(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		
		String fileName = req.getParameter("fileName");
		log.info("fileName : " + fileName);
		
		File noticeFile = new File(DEFAULT_PATH);
		File file = new File(noticeFile.getAbsolutePath(), fileName);
		log.info("file.getNamee() : " + file.getName());
		
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
	
	
	@GetMapping("/noticeList")
	public String noticeBoardList(Model model) {
	        model.addAttribute("noticeList", noticeBoardService.noticeBoardList());
	        return "views/notice/noticeList"; 
	    }
	
	@GetMapping("/noticeDetail")
	public String noticeBoardDetail(Model model, @RequestParam("no") int no) {
		noticeBoardService.incrementReadCount(no);
		model.addAttribute("noticeBoard", noticeBoardService.getNoticeBoard(no));
		return "views/notice/noticeDetail";
	}
	
	@GetMapping("/addNoticeBoard")
	public String addNoticeBoard() {
		return "views/notice/noticeWriteForm";
	}
	
	@PostMapping("/addNoticeBoard")
	public String addNoticeBoard(NoticeBoard noticeBoard,
			HttpServletRequest req,
			@RequestParam(value="addFile", required=false) MultipartFile multipartFile) throws IOException{
		// 관리자 아이디만 등록 가능
		/* noticeBoard.setWriter("admin");
		 * noticeBoard.setPass("admin"); */
		System.out.println("originName : " + multipartFile.getOriginalFilename());
		System.out.println("name : " + multipartFile.getName());
		
		if(multipartFile != null && !multipartFile.isEmpty()) {
			File noticeFile = new File(DEFAULT_PATH);
			log.info("noticeFile 절대경로 : " + noticeFile.getAbsolutePath());
			log.info("noticeFile 경로 : " + noticeFile.getPath());
			log.info("exist : " + noticeFile.exists() + ", dir : " + noticeFile.isDirectory());
		
		if (!noticeFile.isDirectory() && !noticeFile.exists()) {
			noticeFile.mkdirs();
		}
		
		UUID uid = UUID.randomUUID();
		String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		String saveName = multipartFile.getOriginalFilename();
		
		File file = new File(noticeFile.getAbsolutePath(), saveName);
		log.info("file 절대경로 : " + file.getAbsolutePath());
		log.info("file 경로 : " + file.getPath());
		
		multipartFile.transferTo(file);
		noticeBoard.setFile1(saveName);
		
		} else {
			log.info("파일이 업로드 되지 않았습니다.");
		}
		
		noticeBoardService.addNoticeBoard(noticeBoard);
		return "redirect:/noticeList";
	}
	
	@PostMapping("/noticeUpdateForm")
	public String updateNoticeBoard(Model model, HttpServletResponse resp, PrintWriter out,
			@RequestParam("no") int no, @RequestParam("pass") String pass) {
		
		NoticeBoard noticeBoard = noticeBoardService.getNoticeBoard(no);
		
		if(! noticeBoard.getPass().equals(pass)) {
			 resp.setContentType("text/html; charset=utf-8");
			 out.println("<script>");
			 out.println(" alert('비밀번호가 맞지 않습니다!!');");
			 out.println(" history.back();");
			 out.println("</script>");
			 return null;
		}
		
		model.addAttribute("noticeBoard", noticeBoard);
	    return "views/notice/noticeUpdateForm";
	}
	
	@PostMapping("/noticeUpdate")
	public String updateNoticeBoard (NoticeBoard noticeBoard,
			HttpServletResponse resp, PrintWriter out) {
		
		NoticeBoard nBoard = noticeBoardService.getNoticeBoard(noticeBoard.getNo());
		
		if(! nBoard.getPass().equals(noticeBoard.getPass())) {
			 resp.setContentType("text/html; charset=utf-8");
			 out.println("<script>");
			 out.println(" alert('비밀번호가 맞지 않습니다~!!');");
			 out.println(" history.back();");
			 out.println("</script>");
			 return null;
		}
		
		noticeBoardService.updateNoticeBoard(noticeBoard);
		return "redirect:/noticeList";
	}
	
	@PostMapping("/deleteNotice")
	public String deleteNoticeBoard(
			HttpServletResponse resp, PrintWriter out, 
			@RequestParam("no") int no, @RequestParam("pass") String pass) {
		
		NoticeBoard noticeBoard = noticeBoardService.getNoticeBoard(no);
		
		if(! noticeBoard.getPass().equals(pass)) {
			 resp.setContentType("text/html; charset=utf-8");
			 out.println("<script>");
			 out.println(" alert('비밀번호가 맞지 않습니다~!!');");
			 out.println(" history.back();");
			 out.println("</script>");
			 return null;
		}
		
		noticeBoardService.deleteNoticeBoard(no);
		return "redirect:/noticeList";
	}
	

}

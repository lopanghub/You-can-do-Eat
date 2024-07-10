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
import com.springbootstudy.app.domain.EventBoard;
import com.springbootstudy.app.service.EventBoardService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class EventBoardController {
	
	private final EventBoardService eventBoardService;
	
	private static final String DEFAULT_PATH  = "src/main/resources/static/files/";
	
	@GetMapping("/eventFileDownload")
	public void eventFileDownload(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		
		String fileName = req.getParameter("fileName");
		log.info("fileName : " + fileName);
		
		File eventFile = new File(DEFAULT_PATH);
		File file = new File(eventFile.getAbsolutePath(), fileName);
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
	
	
	@GetMapping("/eventList")
	public String eventBoardList(Model model) {
	        model.addAttribute("eventList", eventBoardService.eventBoardList());
	        return "views/event/eventList"; 
	    }
	
	@GetMapping("/eventDetail")
	public String eventBoardDetail(Model model, @RequestParam("no") int no) {
		eventBoardService.incrementReadCount(no);
		model.addAttribute("eventBoard", eventBoardService.getEventBoard(no));
		return "views/event/eventDetail";
	}
	
	@GetMapping("/addEventBoard")
	public String addEventBoard() {
		return "views/event/eventWriteForm";
	}
	
	@PostMapping("/addEventBoard")
	public String addEventBoard(EventBoard eventBoard,
			HttpServletRequest req,
			@RequestParam(value="addFile", required=false) MultipartFile multipartFile) throws IOException{
		// 관리자 아이디만 등록 가능
		/* eventBoard.setWriter("admin");
		 * eventBoard.setPass("admin"); */
		System.out.println("originName : " + multipartFile.getOriginalFilename());
		System.out.println("name : " + multipartFile.getName());
		
		if(multipartFile != null && !multipartFile.isEmpty()) {
			File eventFile = new File(DEFAULT_PATH);
			log.info("noticeFile 절대경로 : " + eventFile.getAbsolutePath());
			log.info("noticeFile 경로 : " + eventFile.getPath());
			log.info("exist : " + eventFile.exists() + ", dir : " + eventFile.isDirectory());
		
		if (!eventFile.isDirectory() && !eventFile.exists()) {
			eventFile.mkdirs();
		}
		
		UUID uid = UUID.randomUUID();
		String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
		String saveName = multipartFile.getOriginalFilename();
		
		File file = new File(eventFile.getAbsolutePath(), saveName);
		log.info("file 절대경로 : " + file.getAbsolutePath());
		log.info("file 경로 : " + file.getPath());
		
		multipartFile.transferTo(file);
		eventBoard.setFile1(saveName);
		
		} else {
			log.info("파일이 업로드 되지 않았습니다.");
		}
		
		eventBoardService.addEventBoard(eventBoard);
		return "redirect:/eventList";
	}
	
	@PostMapping("/eventUpdateForm")
	public String updateEventBoard(Model model, HttpServletResponse resp, PrintWriter out,
			@RequestParam("no") int no, @RequestParam("pass") String pass) {
		
		EventBoard eventBoard = eventBoardService.getEventBoard(no);
		
		if(! eventBoard.getPass().equals(pass)) {
			 resp.setContentType("text/html; charset=utf-8");
			 out.println("<script>");
			 out.println(" alert('비밀번호가 맞지 않습니다!!');");
			 out.println(" history.back();");
			 out.println("</script>");
			 return null;
		}
		
		model.addAttribute("eventBoard", eventBoard);
	    return "views/event/eventUpdateForm";
	}
	
	@PostMapping("/eventUpdate")
	public String updateEventBoard (EventBoard eventBoard,
			HttpServletResponse resp, PrintWriter out) {
		
		EventBoard eBoard = eventBoardService.getEventBoard(eventBoard.getNo());
		
		if(! eBoard.getPass().equals(eventBoard.getPass())) {
			 resp.setContentType("text/html; charset=utf-8");
			 out.println("<script>");
			 out.println(" alert('비밀번호가 맞지 않습니다~!!');");
			 out.println(" history.back();");
			 out.println("</script>");
			 return null;
		}
		
		eventBoardService.updateEventBoard(eventBoard);
		return "redirect:/eventList";
	}
	
	@PostMapping("/deleteEvent")
	public String deleteEventBoard(
			HttpServletResponse resp, PrintWriter out, 
			@RequestParam("no") int no, @RequestParam("pass") String pass) {
		
		EventBoard eventBoard = eventBoardService.getEventBoard(no);
		
		if(! eventBoard.getPass().equals(pass)) {
			 resp.setContentType("text/html; charset=utf-8");
			 out.println("<script>");
			 out.println(" alert('비밀번호가 맞지 않습니다~!!');");
			 out.println(" history.back();");
			 out.println("</script>");
			 return null;
		}
		
		eventBoardService.deleteEventBoard(no);
		return "redirect:/eventList";
	}
	

}

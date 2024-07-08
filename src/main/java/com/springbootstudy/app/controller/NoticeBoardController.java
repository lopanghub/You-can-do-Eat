package com.springbootstudy.app.controller;

import java.io.PrintWriter;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.resource.HttpResource;

import com.springbootstudy.app.domain.NoticeBoard;
import com.springbootstudy.app.service.NoticeBoardService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NoticeBoardController {
	
	private final NoticeBoardService noticeBoardService;
	
	@GetMapping("/noticeList")
	public String noticeBoardList(Model model) {
	        model.addAttribute("noticeList", noticeBoardService.noticeBoardList());
	        return "views/noticeList"; 
	    }
	
	@GetMapping("/noticeDetail")
	public String noticeBoardDetail(Model model, @RequestParam("no") int no) {
		model.addAttribute("noticeBoard", noticeBoardService.getNoticeBoard(no));
		return "views/noticeDetail";
	}
	
	@GetMapping("/addNoticeBoard")
	public String addNoticeBoard() {
		return "views/noticeWriteForm";
	}
	
	@PostMapping("/addNoticeBoard")
	public String addNoticeBoard(NoticeBoard noticeBoard) {
		// 관리자 아이디만 등록 가능
		noticeBoard.setWriter("admin");
		noticeBoard.setPass("admin"); 
		
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
	    return "views/noticeUpdateForm";
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

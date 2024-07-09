package com.springbootstudy.app.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.springbootstudy.app.domain.MemberShip;
import com.springbootstudy.app.service.MemberService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	private static final String DEFAULT_PATH = "src/main/resources/static/profile/";

	// 회원가입
	@PostMapping({ "joinResult" })
	public String joinResult(MemberShip member, @RequestParam(value = "emailId") String emailId,
			@RequestParam(value = "emailDomain") String emailDomain, @RequestParam(value = "mobile1") String mobile1,
			@RequestParam(value = "mobile2") String mobile2, @RequestParam(value = "mobile3") String mobile3,
			HttpServletRequest req, @RequestParam(value = "emailGet", defaultValue = "false") boolean emailGat,
			@RequestParam(value = "profileImage") MultipartFile multipartFile) throws IOException {

		if (!multipartFile.isEmpty()) {
			String realPath = req.getServletContext().getRealPath(DEFAULT_PATH);

			UUID uid = UUID.randomUUID();
			String saveName = uid.toString() + "_" + multipartFile.getOriginalFilename();
			File file = new File(realPath, saveName);
			System.out.println("joinResult : " + file.getName());

			multipartFile.transferTo(file);
		}

		member.setEmail(emailId + "@" + emailDomain);
		member.setMobile(mobile1 + "-" + mobile2 + "-" + mobile3);
		member.setEmailGet(emailGat);
		member.setGerdonalGet(emailGat);

		memberService.addMember(member);

		return "views/login";
	}

	// 회원가입
	@GetMapping("/membership")
	public String mebership(Model model) {
		return "views/join";
	}

	@GetMapping("/login")
	public String login() throws ServletException, IOException {
		return "views/login";
	}

	@PostMapping("/login")
	public String login(Model model, @RequestParam("userId") String id, @RequestParam("pass") String pass,
			HttpSession session, HttpServletResponse response) throws ServletException, IOException {
		int result = memberService.login(id, pass);

		if (result == -1) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('존재하지 않는 아이디 입니다.');");
			out.print("history.back();");
			out.print("</script>");
			return null;
		} else if (result == 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('비밀번호가 다릅니다.');");
			out.print("history.back();");
			out.print("</script>");
			return null;
		}
		MemberShip member = memberService.getMember(id);
		session.setAttribute("isLogin", true);

		session.setAttribute("member", member);
		System.out.println("member.id : " + member);

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}

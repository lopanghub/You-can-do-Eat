package com.springbootstudy.app.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
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
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MemberController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MemberService memberService;

	private static final String DEFAULT_PATH = "src/main/resources/static/profiles/";

	@PostMapping("/delete")
	public String deleteUser(MemberShip member, HttpServletResponse pesp, Model model, HttpSession session,
			@RequestParam(value = "id", required = false) String id) throws IOException {
		MemberShip m = (MemberShip) session.getAttribute("member");
		member = memberService.getMember(m.getId());
		session.setAttribute("member", member);
		System.out.println(id);
		memberService.deleteUser(id);
		return "redirect:login";
	}

	/*
	 * // 회원탈퇴 페이지
	 * 
	 * @GetMapping("/userdelete") public String userdelete() { return
	 * "views/joindelete"; }
	 */

	// 마이페이지
	@GetMapping("/memberMyPage")
	public String myPageForm() {
		return "views/mypage";
	}

	// 회원가입
	@PostMapping({ "/memberUpdateResult" })
	public String memberUpdateResult(MemberShip member, HttpServletResponse pesp, Model model, HttpSession session,
			@RequestParam(value = "emailId") String emailId, @RequestParam(value = "emailDomain") String emailDomain,
			@RequestParam(value = "mobile1") String mobile1, @RequestParam(value = "mobile2") String mobile2,
			@RequestParam(value = "mobile3") String mobile3, HttpServletRequest req,
			@RequestParam(value = "emailGet", defaultValue = "false") boolean emailGat,
			@RequestParam(value = "gerdonalGet", defaultValue = "false") boolean gerdonalGet,
			@RequestParam(value = "profileImage") MultipartFile multipartFile,
			@RequestParam(value = "pass1", required = false) String pass) throws IOException {

		// 비밀번호를 변경 안해도 수정 가능
		if (pass == null || pass.isEmpty()) {
			MemberShip existingMember = memberService.getMemberById(member.getId());
			member.setPass(existingMember.getPass());
		} else {
			member.setPass(passwordEncoder.encode(pass));
		}

		if (multipartFile != null && !multipartFile.isEmpty()) {
			File parent = new File(DEFAULT_PATH);

			// 폴더가 존재하지 않으면 생성
			if (!parent.isDirectory() && !parent.exists()) {
				parent.mkdirs();
			}
			UUID uid = UUID.randomUUID();
			String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
			String saveName = uid.toString() + "." + extension;

			File file = new File(parent.getAbsolutePath(), saveName);

			multipartFile.transferTo(file);

			member.setProfile(saveName);
		} else {
			System.out.println();
		}
		// System.out.println("memberUpdateResult : " + mobile1 + ", " + mobile2 + ", "
		// + mobile3);
		member.setEmail(emailId + "@" + emailDomain);
		member.setMobile(mobile1 + "-" + mobile2 + "-" + mobile3);
		member.setEmailGet(emailGat);
		member.setGerdonalGet(gerdonalGet);
		// 회원 수정하면서 DB에서 가져오기
		memberService.updateMember(member);
		MemberShip m = (MemberShip) session.getAttribute("member");
		member = memberService.getMember(m.getId());
		session.setAttribute("member", member);

		// return "views/joinupdate";
		return "redirect:/memberMyPage";
	}

	// 회원 수정 폼
	@GetMapping("/memberUpdateForm")
	public String memberUpdateForm() {
		return "views/joinupdate";
	}

	// 회원가입
	@PostMapping({ "joinResult" })
	public String joinResult(MemberShip member, @RequestParam(value = "emailId") String emailId,
			@RequestParam(value = "emailDomain") String emailDomain, @RequestParam(value = "mobile1") String mobile1,
			@RequestParam(value = "mobile2") String mobile2, @RequestParam(value = "mobile3") String mobile3,
			HttpServletRequest req, @RequestParam(value = "emailGet", defaultValue = "false") boolean emailGat,
			@RequestParam(value = "gerdonalGet", defaultValue = "false") boolean gerdonalGet,
			@RequestParam(value = "profileImage") MultipartFile multipartFile) throws IOException {

		if (multipartFile != null && !multipartFile.isEmpty()) {
			File parent = new File(DEFAULT_PATH);

			// 폴더가 존재하지 않으면 생성
			if (!parent.isDirectory() && !parent.exists()) {
				parent.mkdirs();
			}
			UUID uid = UUID.randomUUID();
			String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
			String saveName = uid.toString() + "." + extension;

			File file = new File(parent.getAbsolutePath(), saveName);

			multipartFile.transferTo(file);

			member.setProfile(saveName);
		} else {
			System.out.println();
		}

		member.setEmail(emailId + "@" + emailDomain);
		member.setMobile(mobile1 + "-" + mobile2 + "-" + mobile3);
		member.setEmailGet(emailGat);
		member.setGerdonalGet(gerdonalGet);

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
		// System.out.println("member.id : " + member.getId());
		System.out.println("member.id : " + member.getId());

		log.info("로그인 성공: 세션 ID={}, 회원 ID={}", session.getId(), member.getId());

		return "redirect:/";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

}

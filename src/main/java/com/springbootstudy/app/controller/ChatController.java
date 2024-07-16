package com.springbootstudy.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.springbootstudy.app.domain.MemberShip;
import com.springbootstudy.app.domain.Message;
import com.springbootstudy.app.handler.ChatHandler;
import com.springbootstudy.app.mapper.UserMapper;
import com.springbootstudy.app.service.MemberService;
import com.springbootstudy.app.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@Configuration
public class ChatController {
	
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private MemberService memberService;
	
	private final UserService userService;
	
	@Autowired
    private ChatHandler chatHandler;

    @Autowired
    public ChatController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/chat/sendMessage")
    public String getUserList(Model model, MemberShip member) {
        List<MemberShip> userList = userService.getAllUsers();
        model.addAttribute("membership", userList);
        for (MemberShip user : userList) {
            System.out.println("User ID: " + user.getId() + ", Name: " + user.getName());
        }
        return "chat"; // user-list.html로 이동
    }
	
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // WebSocket에서 메시지 수신 시 처리
        String payload = message.getPayload();
        // 메시지 전달 처리
        session.sendMessage(new TextMessage("Received: " + payload));
    }

	@PostMapping("/chat/sendMessage")
    @ResponseBody
    public void sendMessage(@RequestBody String content, HttpSession session) {
        String userId = (String) session.getAttribute("id"); // 세션에서 사용자 아이디 가져오기
        //System.out.println(userId);
        String sessionId = session.getId(); // 세션 ID 가져오기
//        System.out.println("Session ID: " + sessionId);
        Message message = new Message(sessionId, content);
    }
	
	@GetMapping("/chatmessage")
	public String chatIdmessage(Model model, HttpSession session, @RequestParam(value = "id", required = false) String id) {
		List<MemberShip> userList = userService.getAllUsers();
		session.setAttribute("id", id);
		model.addAttribute("id", id);
		model.addAttribute("userList", userList);
		return "views/chat";
	}
}
package com.springbootstudy.app.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.springbootstudy.app.domain.MemberShip;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ChatService {
	 // 예시 메서드: 데이터베이스에서 userId로 사용자 정보 조회
    public MemberShip getUserById(String id) {
        // 여기서는 단순히 예시로 만들었으므로 실제 로직은 데이터베이스 조회를 구현해야 합니다.
    	MemberShip user = new MemberShip();
        user.setId(id);
        user.setName("qwerqwer");
        // 여기서 데이터베이스에서 추가적인 정보를 조회할 수 있습니다.
        return user;
    }
    public void sendMessage(WebSocketSession session, String message) {
    	try {
            TextMessage textMessage = new TextMessage(message);
            session.sendMessage(textMessage);
        } catch (IOException e) {
            log.error("Error sending message to session {}: {}", session.getId(), e.getMessage());
            // 예외 처리 로직 추가
        }
    }
}

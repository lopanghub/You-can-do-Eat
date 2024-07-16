package com.springbootstudy.app.handler;

import com.springbootstudy.app.domain.MemberShip;
import com.springbootstudy.app.service.ChatMessageService;
import com.springbootstudy.app.service.ChatService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private ChatMessageService chatMessageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	log.info("클라이언트 접속 : " + session);
        String userId = extractUserIdFromSession(session);
        if (userId != null) {
            sessions.put(userId, session);
            session.sendMessage(new TextMessage("You are connected as " + userId));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload---"+payload);
        session.sendMessage(message);
        
        /*
        String[] parts = payload.split(":");
        if (parts.length >= 2) {
            String destUserId = parts[0].trim();
            String content = payload.substring(destUserId.length() + 1).trim();
            sendMessageToUser(destUserId, session.getId() + ": " + content);
        } else {
            session.sendMessage(new TextMessage("Error: Invalid message format."));
        }
        */
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = extractUserIdFromSession(session);
        if (userId != null) {
            sessions.remove(userId);
        }
    }

    // 부가서비스
    private String extractUserIdFromSession(WebSocketSession session) {
        String userId = session.getUri().toString().split("/")[2];
        return userId;
    }

    public void sendMessageToUser(String userId, String message) throws IOException {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        } else {
            log.warn("Session not found or closed for user: " + userId);
            // Optional: Handle case where session is not found or closed
        }
    }

    // 예제를 위한 추가 메서드: 데이터베이스에서 유저 정보 조회
    public MemberShip getUserById(String id) {
        return chatService.getUserById(id);
    }
}

package com.springbootstudy.app.handler;

import com.springbootstudy.app.domain.ChatMessage;
import com.springbootstudy.app.domain.MemberShip;
import com.springbootstudy.app.mapper.MemberMapper;
import com.springbootstudy.app.service.ChatMessageService;
import com.springbootstudy.app.service.ChatService;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.session.SqlSession;
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
@Log4j2
public class ChatHandler2 extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private static final String TARGET_USER_ID = "qwerqwer";

    @Autowired
    private ChatService chatService;

    @Autowired
    private SqlSession sqlSession;
    
    @Autowired
    private ChatMessageService chatMessageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = extractUserIdFromSession(session);
        if (userId != null) {
            sessions.put(userId, session);
            session.sendMessage(new TextMessage("You are connected as " + userId));
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        String[] parts = payload.split(":");
        if (parts.length >= 2) {
            String destUserId = parts[0].trim();
            String content = payload.substring(destUserId.length() + 1).trim();

            // 특정 사용자(TARGET_USER_ID)와만 채팅할 수 있도록 처리
            if (destUserId.equals(TARGET_USER_ID)) {
                // 데이터베이스에 채팅 메시지 저장
                String senderId = extractUserIdFromSession(session);

                // 사용자에게 메시지 전송
                sendMessageToUser(destUserId, session.getId() + ": " + content);
            } else {
                session.sendMessage(new TextMessage("Error: You can only chat with user " + TARGET_USER_ID));
            }
        } else {
            session.sendMessage(new TextMessage("Error: Invalid message format."));
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = extractUserIdFromSession(session);
        if (userId != null) {
            sessions.remove(userId);
        }
    }

    private String extractUserIdFromSession(WebSocketSession session) {
        String userId = session.getUri().toString().split("/")[2];
        return userId;
    }

    public void sendMessageToUser(String userId, String message) throws IOException {
        WebSocketSession session = sessions.get(userId);
        if (session != null && session.isOpen()) {
            chatService.sendMessage(session, message); // ChatService를 통해 메시지 전송
        } else {
            log.warn("Session not found or closed for user: " + userId);
            // Optional: Handle case where session is not found or closed
        }
    }

    // 예제를 위한 추가 메서드: 데이터베이스에서 유저 정보 조회
    public MemberShip getUserById(String id) {
        MemberMapper memberMapper = sqlSession.getMapper(MemberMapper.class);
        return memberMapper.getMemberShip(id);
    }
}

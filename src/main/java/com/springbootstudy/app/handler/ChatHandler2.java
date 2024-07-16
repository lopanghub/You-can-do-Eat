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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
public class ChatHandler2 extends TextWebSocketHandler {

    private static final String CHAT_TOPIC_PREFIX = "/topic/chat/";

    // 사용자별로 WebSocketSession을 관리할 맵
    private static Map<String, Set<WebSocketSession>> userSessions = new ConcurrentHashMap<>();

    @Autowired
    private ChatService chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = extractUserIdFromSession(session);
        if (userId != null) {
            userSessions.computeIfAbsent(userId, k -> new HashSet<>()).add(session);
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

            // 사용자에게 메시지 전송
            sendMessageToUser(destUserId, session.getId() + ": " + content);
        } else {
            session.sendMessage(new TextMessage("Error: Invalid message format."));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = extractUserIdFromSession(session);
        if (userId != null) {
            Set<WebSocketSession> sessions = userSessions.get(userId);
            if (sessions != null) {
                sessions.remove(session);
                if (sessions.isEmpty()) {
                    userSessions.remove(userId);
                }
            }
        }
    }

    private String extractUserIdFromSession(WebSocketSession session) {
        String userId = session.getUri().toString().split("/")[2];
        return userId;
    }

    public void sendMessageToUser(String userId, String message) throws IOException {
        Set<WebSocketSession> sessions = userSessions.get(userId);
        if (sessions != null && !sessions.isEmpty()) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                } else {
                    log.warn("Session closed for user: " + userId);
                }
            }
        } else {
            log.warn("No active sessions found for user: " + userId);
        }
    }
}
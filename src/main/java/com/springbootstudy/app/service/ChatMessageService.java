package com.springbootstudy.app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.ChatMessage;
import com.springbootstudy.app.mapper.ChatMessageMapper;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper; // MyBatis Mapper

    public void saveChatMessage(String roomId, String senderId, String content) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRoomId(roomId);
        chatMessage.setSenderId(senderId);
        chatMessage.setContent(content);
        chatMessage.setSentAt(LocalDateTime.now());

        chatMessageMapper.insertChatMessage(chatMessage);
    }
}

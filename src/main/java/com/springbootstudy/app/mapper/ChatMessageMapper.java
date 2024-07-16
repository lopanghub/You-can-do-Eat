package com.springbootstudy.app.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.springbootstudy.app.domain.ChatMessage;

@Mapper
public interface ChatMessageMapper {
    void insertChatMessage(ChatMessage chatMessage);
}
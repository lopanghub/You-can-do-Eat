package com.springbootstudy.app.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
	private Long messageId;
    private String roomId; // 채팅 방 ID
    private String senderId; // 발신자 ID
    private String content; // 채팅 내용
    private LocalDateTime sentAt; // 전송 시간
}

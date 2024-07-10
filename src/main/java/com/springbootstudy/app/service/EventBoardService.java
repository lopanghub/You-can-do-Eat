package com.springbootstudy.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.EventBoard;
import com.springbootstudy.app.mapper.EventBoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EventBoardService {
	
	@Autowired
	private EventBoardMapper eventBoardMapper;

	// 현재 페이지 글 리스트 반환
	public List<EventBoard> eventBoardList(){
		return eventBoardMapper.eventBoardList();
	}
	
	// no에 해당하는 게시글 반환 (조회수 카운트 추가)
    public EventBoard getEventBoard(int no) {
    	return eventBoardMapper.getEventBoard(no);
    }
    
    // 게시글 추가
    public void addEventBoard(EventBoard eventBoard) {
    	eventBoardMapper.addEventBoard(eventBoard);
    }
    
    // 게시글 수정
    public void updateEventBoard(EventBoard eventBoard) {
    	eventBoardMapper.updateEventBoard(eventBoard);
    }
    
    // 게시글 삭제
    public void deleteEventBoard(int no) {
    	eventBoardMapper.deleteEventBoard(no);
    }
    
    // 조회수 증가
    public void incrementReadCount(int no) {
    	eventBoardMapper.incrementReadCount(no);
    }
    
   
}

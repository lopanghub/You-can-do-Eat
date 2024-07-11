package com.springbootstudy.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.springbootstudy.app.domain.EventBoard;

@Mapper
public interface EventBoardMapper {
	
	
	List<EventBoard> eventBoardList();
	
	EventBoard getEventBoard(int no);
	
	void addEventBoard(EventBoard eventBoard);
	
	void updateEventBoard(EventBoard eventBoard);
	
	void deleteEventBoard(int no);

	void incrementReadCount(int no);
}

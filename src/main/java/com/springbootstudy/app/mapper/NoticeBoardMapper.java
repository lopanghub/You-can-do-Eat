package com.springbootstudy.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.springbootstudy.app.domain.NoticeBoard;

@Mapper
public interface NoticeBoardMapper {
	
	
	List<NoticeBoard> noticeBoardList();
	
	NoticeBoard getNoticeBoard(int no);
	
	void addNoticeBoard(NoticeBoard noticeBoard);
	
	void updateNoticeBoard(NoticeBoard noticeBoard);
	
	void deleteNoticeBoard(int no);

	void incrementReadCount(int no);
}

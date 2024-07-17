package com.springbootstudy.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.Community;
import com.springbootstudy.app.domain.NoticeBoard;
import com.springbootstudy.app.mapper.NoticeBoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeBoardService {
	
	@Autowired
	private NoticeBoardMapper noticeBoardMapper;

	// 현재 페이지 글 리스트 반환
	public List<NoticeBoard> noticeBoardList(){
		return noticeBoardMapper.noticeBoardList();
	}
	
	// no에 해당하는 게시글 반환 (조회수 카운트 추가)
    public NoticeBoard getNoticeBoard(int no) {
    	return noticeBoardMapper.getNoticeBoard(no);
    }
    
    // 게시글 추가
    public void addNoticeBoard(NoticeBoard noticeBoard) {
    	noticeBoardMapper.addNoticeBoard(noticeBoard);
    }
    
    // 게시글 수정
    public void updateNoticeBoard(NoticeBoard noticeBoard) {
    	noticeBoardMapper.updateNoticeBoard(noticeBoard);
    }
    
    // 게시글 삭제
    public void deleteNoticeBoard(int no) {
    	noticeBoardMapper.deleteNoticeBoard(no);
    }
    
    // 조회수 증가
    public void incrementReadCount(int no) {
    	noticeBoardMapper.incrementReadCount(no);
    }
    
   
}

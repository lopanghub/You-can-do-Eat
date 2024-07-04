package com.springbootstudy.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.NoticeBoard;
import com.springbootstudy.app.mapper.NoticeBoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
public interface NoticeBoardService {

	// 현재 페이지 글 리스트 반환
	List<NoticeBoard> noticeBoardList();
	
	// no에 해달하는 게시글 반환
    NoticeBoard getNoticeBoard(int no);
    
    // 게시글 추가
    void addNoticeBoard(NoticeBoard noticeBoard);
    
    
    // 비밀번호 체크
    public boolean isPassCheck(int no, String pass);
    
    // 게시글 수정
    void updateNoticeBoard(NoticeBoard noticeBoard);
    
    // 게시글 삭제
    void deleteNoticeBoard(int no);
   
}

package com.springbootstudy.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.NoticeBoard;
import com.springbootstudy.app.mapper.NoticeBoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NoticeBoardServiceImpl implements NoticeBoardService{

	@Autowired
	private NoticeBoardMapper noticeBoardMapper;
	
	
	@Override
	public List<NoticeBoard> noticeBoardList() {
		return noticeBoardMapper.noticeBoardList();
	
	}

	//  디테일 페이지 가져오기
	@Override
	public NoticeBoard getNoticeBoard(int no) {
		return noticeBoardMapper.getNoticeBoard(no);
	}

	@Override
	public void addNoticeBoard(NoticeBoard noticeBoard) {
		noticeBoardMapper.addNoticeBoard(noticeBoard);
	}

	@Override
	public boolean isPassCheck(int no, String pass) {
//		NoticeBoard notice = noticeBoardMapper.getNoticeBoard(no);
//        return notice != null && notice.getPass().equals(pass);
		return false; 
	}

	@Override
	public void updateNoticeBoard(NoticeBoard noticeBoard) {
		noticeBoardMapper.updateNoticeBoard(noticeBoard);
		
	}

	@Override
	public void deleteNoticeBoard(int no) {
		noticeBoardMapper.deleteNoticeBoard(no);	
	}
	
	
}

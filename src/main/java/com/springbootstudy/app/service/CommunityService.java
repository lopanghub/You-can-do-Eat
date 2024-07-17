package com.springbootstudy.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.Community;
import com.springbootstudy.app.mapper.CommunityMapper;
import com.springbootstudy.app.mapper.NoticeBoardMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommunityService {
	
	@Autowired
	private CommunityMapper communityMapper;

	// 현재 페이지 글 리스트 반환
	public List<Community> communityList(){
		return communityMapper.communityBoardList();
	}
	
	// no에 해당하는 게시글 반환 (조회수 카운트 추가)
    public Community getCommunityBoard(int no) {
    	return communityMapper.getCommunityBoard(no);
    }
    
    // 게시글 추가
    public void addCommunityBoard(Community community) {
    	communityMapper.addCommunityBoard(community);
    }
    
    // 게시글 수정
    public void updateCommunityBoard(Community community) {
    	communityMapper.updateCommunityBoard(community);
    }
    
    // 게시글 삭제
    public void deleteCommunityBoard(int no) {
    	communityMapper.deleteCommunityBoard(no);
    }
    
    // 조회수 증가
    public void incrementReadCount(int no) {
    	communityMapper.incrementReadCount(no);
    }
    
   
}

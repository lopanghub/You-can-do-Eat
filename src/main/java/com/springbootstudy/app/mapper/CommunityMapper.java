package com.springbootstudy.app.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;


import com.springbootstudy.app.domain.Community;

@Mapper
public interface CommunityMapper {
	
	
	List<Community> communityBoardList();
	
	Community getCommunityBoard(int no);
	
	void addCommunityBoard(Community community);
	
	void updateCommunityBoard(Community community);
	
	void deleteCommunityBoard(int no);

	void incrementReadCount(int no);
}

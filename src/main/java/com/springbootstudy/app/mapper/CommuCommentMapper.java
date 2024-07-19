package com.springbootstudy.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.domain.CommuComment;

@Mapper
public interface CommuCommentMapper {
	
	//boardno의 댓글리스트
	List<CommuComment> commuCommentList(@Param("no") int no);
	
	//댓글 카운트
	int commuCommentCount(int no);
	//댓글 추가
	void insertCommuComment(CommuComment commuComment);
	//댓글 업데이트
	void updateCommuComment(CommuComment commuComment);
	//댓글 삭제 
	void deleteCommuComment(int commuCommentId);
	//댓글 업데이트후 조회
	CommuComment selectCommuCommentById(@Param("commuCommentId")int commuCommentId);
	
	void deleteCommuCommentByNo(int no);
}


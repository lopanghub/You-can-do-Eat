package com.springbootstudy.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.springbootstudy.app.domain.Comment;

@Mapper
public interface CommuCommentMapper {
	
	//boardno의 댓글리스트
	List<Comment> commuCommentList(@Param("no") int no);
	
	//댓글 카운트
	int commuCommentCount(int no);
	//댓글 추가
	void insertCommuComment(Comment comment);
	//댓글 업데이트
	void updateCommuComment(Comment comment);
	//댓글 삭제 
	void deleteCommuComment(int commentId);
	//댓글 업데이트후 조회
	Comment selectCommuCommentById(@Param("commentId")int commentId);
	
	void deleteCommuCommentByNo(int board);
}


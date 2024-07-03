package com.springbootstudy.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.springbootstudy.app.domain.Comment;

@Mapper
public interface CommentMapper {
	//boardno의 댓글리스트
	List<Comment> commentList(@Param("boardNo") int boardNo);
	//댓글 카운트
	int commentCount(int boardNo);
	//댓글 추가
	void insertComment(Comment comment);
	
	//댓글 업데이트
	void updateComment(Comment comment);
	
	void deleteComment(int commentId);
	
	Comment selectCommentById(@Param("commentId")int commentId);
	
}

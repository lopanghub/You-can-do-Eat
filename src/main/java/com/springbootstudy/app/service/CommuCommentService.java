package com.springbootstudy.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.domain.CommuComment;
import com.springbootstudy.app.mapper.CommentMapper;
import com.springbootstudy.app.mapper.CommuCommentMapper;

@Service
public class CommuCommentService {
	
	@Autowired
	private CommuCommentMapper commuCommentmapper;
	
	//댓글 리스트
	public List<CommuComment>  commuCommentList(int no){
		return  commuCommentmapper.commuCommentList(no);
	}
	
	//댓글 리스트 카운트
	public int commuCommentCount(int no) {
		return commuCommentmapper.commuCommentCount(no);
	}
	  // 댓글추가
	  public CommuComment addCommuComment(int no, String commuCommentContent,String memberId) {
		  CommuComment commuComment = new CommuComment();
		  commuComment.setNo(no);
		  commuComment.setCommuCommentContent(commuCommentContent);
		  commuComment.setMemberId(memberId);
		  System.out.println("commuComment:" + commuComment.getCommuCommentContent());
	        commuCommentmapper.insertCommuComment(commuComment);
	        return commuCommentmapper.selectCommuCommentById(commuComment.getCommuCommentId());
	    }
	  // 댓글 업데이트
	  public CommuComment updatecCommuComment(int commuCommentId, String commuCommentContent) {
		  CommuComment comment = new CommuComment();
	        comment.setCommuCommentId(commuCommentId);
	        comment.setCommuCommentContent(commuCommentContent);
	        commuCommentmapper.updateCommuComment(comment);
	        return commuCommentmapper.selectCommuCommentById(commuCommentId);
	    }
	  
	  //댓글삭제
	  public CommuComment deleteCommuComment( int  commentId) {
		  commuCommentmapper.deleteCommuComment(commentId);
		  return commuCommentmapper.selectCommuCommentById(commentId);
	  }
	  //boardNo의 댓글삭제
	  public void deleteCommuCommentByNo(int boardNo) {
		  commuCommentmapper.deleteCommuCommentByNo(boardNo);
		  
	  }
}

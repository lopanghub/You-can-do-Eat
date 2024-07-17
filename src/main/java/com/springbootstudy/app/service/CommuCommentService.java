package com.springbootstudy.app.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.mapper.CommentMapper;
import com.springbootstudy.app.mapper.CommuCommentMapper;

@Service
public class CommuCommentService {
	
	@Autowired
	private CommuCommentMapper commuCommentmapper;
	
	//댓글 리스트
	public List<Comment>  commuCommentList(int no){
		return  commuCommentmapper.commuCommentList(no);
	}
	
	//댓글 리스트 카운트
	public int commuCommentCount(int no) {
		return commuCommentmapper.commuCommentCount(no);
	}
	//댓글 평균 내주는 매서드
	  public double calculateAveragePoint(int no) {
	        List<Comment> comments = commuCommentmapper.commuCommentList(no);
	        if (comments.isEmpty()) {
	            return 0.0;
	        }
	        double sum = comments.stream().mapToDouble(Comment::getCommentPoint).sum();
	        double average = sum / comments.size();
	        BigDecimal bd = new BigDecimal(average).setScale(1, RoundingMode.HALF_UP);
	        return bd.doubleValue();
	    }
	  // 댓글추가
	  public Comment addCommuComment(int no, String commentContent, int commentPoint,String memberId) {
	        Comment comment = new Comment();
	        comment.setBoardNo(no);
	        comment.setCommentContent(commentContent);
	        comment.setCommentPoint(commentPoint);
	        comment.setMemberId(memberId);
	        commuCommentmapper.insertCommuComment(comment);
	        return commuCommentmapper.selectCommuCommentById(comment.getCommentId());
	    }
	  // 댓글 업데이트
	  public Comment updatecCommuComment(int commentId, String commentContent, int commentPoint) {
	        Comment comment = new Comment();
	        comment.setCommentId(commentId);
	        comment.setCommentContent(commentContent);
	        comment.setCommentPoint(commentPoint);
	        commuCommentmapper.updateCommuComment(comment);
	        return commuCommentmapper.selectCommuCommentById(commentId);
	    }
	  
	  //댓글삭제
	  public Comment deleteCommuComment( int  commentId) {
		  commuCommentmapper.deleteCommuComment(commentId);
		  return commuCommentmapper.selectCommuCommentById(commentId);
	  }
	  //boardNo의 댓글삭제
	  public void deleteCommuCommentByNo(int boardNo) {
		  commuCommentmapper.deleteCommuCommentByNo(boardNo);
		  
	  }
}

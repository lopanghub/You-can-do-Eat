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

@Service
public class CommentService {
	
	@Autowired
	private CommentMapper commentMapper;
	
	//댓글 리스트
	public List<Comment> commentList(int boardNo){
		return  commentMapper.commentList(boardNo);
	}
	
	//댓글 리스트 카운트
	public int commentCount(int boardNo) {
		return commentMapper.commentCount(boardNo);
	}
	//댓글 평균 내주는 매서드
	  public double calculateAveragePoint(int boardNo) {
	        List<Comment> comments = commentMapper.commentList(boardNo);
	        if (comments.isEmpty()) {
	            return 0.0;
	        }
	        double sum = comments.stream().mapToDouble(Comment::getCommentPoint).sum();
	        double average = sum / comments.size();
	        BigDecimal bd = new BigDecimal(average).setScale(1, RoundingMode.HALF_UP);
	        return bd.doubleValue();
	    }
	  
	  public Comment addComment(int boardNo, String commentContent, int commentPoint) {
	        Comment comment = new Comment();
	        comment.setBoardNo(boardNo);
	        comment.setCommentContent(commentContent);
	        comment.setCommentPoint(commentPoint);
	        commentMapper.insertComment(comment);
	        return comment;
	    }
	  
	  public Comment updateComment(int commentId, String commentContent, int commentPoint) {
	        Comment comment = new Comment();
	        comment.setCommentId(commentId);
	        comment.setCommentContent(commentContent);
	        comment.setCommentPoint(commentPoint);
	        commentMapper.updateComment(comment);
	        return commentMapper.selectCommentById(commentId);
	    }
	  public Comment deleteComment( int  commentId) {
		  commentMapper.deleteComment(commentId);
		  return commentMapper.selectCommentById(commentId);
	  }
}

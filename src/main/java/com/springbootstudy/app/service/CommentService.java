package com.springbootstudy.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.mapper.CommentMapper;

@Service
public class CommentService {
	
	@Autowired
	private CommentMapper commentMapper;
	
	public List<Comment> commentList(int boardNo){
		return  commentMapper.commentList(boardNo);
	}
}
